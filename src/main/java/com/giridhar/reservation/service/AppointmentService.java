package com.giridhar.reservation.service;
import com.giridhar.reservation.Exception.SlotFullException;
import com.giridhar.reservation.dto.BookAppointmentRequest;
import com.giridhar.reservation.model.*;
import com.giridhar.reservation.repository.*;
import com.giridhar.reservation.service.RepresentativeService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.giridhar.reservation.model.SlotAvailability;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class AppointmentService {
    private final PatientRepository patientRepo;
    private final RepresentativeRepository repRepo;
    private final GeoService geoService;
    private final SlotAvailabilityRepository slotRepo;
    private final AppointmentRepository apptRepo;

    private final NextPatientPointerRepository pointerRepo;

    private final RepresentativeService repService;

    private final WaitingListService waitingListSvc;


    public AppointmentService(
            PatientRepository patientRepo,
            RepresentativeRepository repRepo,
            RepresentativeService repService,
            GeoService geoService,
            SlotAvailabilityRepository slotRepo,
            AppointmentRepository apptRepo,
            NextPatientPointerRepository pointerRepo,
            WaitingListService waitingListSvc

    ) {
        this.patientRepo = patientRepo;
        this.repRepo = repRepo;
        this.geoService = geoService;
        this.repService   = repService;
        this.slotRepo = slotRepo;
        this.apptRepo = apptRepo;
        this.pointerRepo = pointerRepo;
        this.waitingListSvc = waitingListSvc;

    }

    @Transactional
    public Appointment book(BookAppointmentRequest req) {
        // 1) Load patient & rep
        Patient p = patientRepo.findById(req.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Representative r = repRepo.findById(req.getRepId())
                .orElseThrow(() -> new IllegalArgumentException("Rep not found"));

        // 2) Find nearest OrganizationLocation
        List<OrganizationLocation> locs = geoService.findNearbyOrNearest(req.getCity(), req.getZip());
        if (locs.isEmpty()) {
            throw new IllegalStateException("No locations found for area");
        }
        OrganizationLocation loc = locs.get(0);

        // 3) Lookup the slot at that location & date & slot
        SlotAvailability slot = slotRepo
                .findByLocationIdAndDateAndSlot(
                        loc.getId(), req.getDate(), req.getSlot()
                )
                .orElseThrow(() -> new IllegalStateException("Slot not available"));

        // 4) Enforce 24-hour cutoff
        Instant slotStart = slot.getDate()
                .atStartOfDay()
                .plus(switch (slot.getSlot()) {
                    case MORNING -> Duration.ofHours(9);
                    case AFTERNOON -> Duration.ofHours(13);
                    case EVENING -> Duration.ofHours(17);
                })
                .toInstant(java.time.ZoneOffset.UTC);
        if (Duration.between(Instant.now(), slotStart).toHours() < 24) {
            throw new IllegalStateException("Cannot book less than 24h before slot");
        }

        // 5) Enforce capacity
        long bookedCount = apptRepo.countBySlotAvailability(slot);
        if (bookedCount >= slot.getCapacity()) {
            // delegate to new service
            UUID entryId = waitingListSvc.addToWaitingList(p, slot.getLocation());
            throw new com.giridhar.reservation.Exception.SlotFullException(
                    "Slot is full – you’ve been placed on the waiting list.",
                    entryId
            );
        }

        // 6) Create & save
        Appointment appt = new Appointment();
        appt.setPatient(p);
        appt.setRep(r);
        appt.setSlotAvailability(slot);
        appt.setBookedAt(Instant.now());
        return apptRepo.save(appt);
    }

    @Transactional
    public void cancel(UUID apptId) {
        Appointment appt = apptRepo.findById(apptId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        SlotAvailability slot = appt.getSlotAvailability();
        Instant slotStart = slot.getDate()
                .atStartOfDay()
                .plus(switch (slot.getSlot()) {
                    case MORNING -> Duration.ofHours(9);
                    case AFTERNOON -> Duration.ofHours(13);
                    case EVENING -> Duration.ofHours(17);
                })
                .toInstant(java.time.ZoneOffset.UTC);
        if (Duration.between(Instant.now(), slotStart).toHours() < 24) {
            throw new IllegalStateException("Cannot cancel less than 24h before slot");
        }

        apptRepo.delete(appt);

    }
    @Transactional
    public void runRoundRobinBooking() {
        // a) fetch all future slots with free capacity
        List<SlotAvailability> openSlots = slotRepo.findByDateAfterAndCapacityGreaterThan(LocalDate.now(), 0);

        // b) load your full patient list
        List<Patient> allPatients = patientRepo.findAll(Sort.by("id"));
        if (openSlots.isEmpty() || allPatients.isEmpty()) return;

        // c) load (or init) the persisted pointer
        NextPatientPointer ptr = pointerRepo.findById(1L)
                .orElseGet(() -> new NextPatientPointer());

        int idx = ptr.getPointer();

        // d) for each slot, book the next patient in round-robin

        for (SlotAvailability slot : openSlots) {
            Patient p = allPatients.get(idx % allPatients.size());
            Representative rep = repService.selectDefaultRep();         // now available
            Appointment appt = new Appointment(p, rep, slot, Instant.now());
            apptRepo.save(appt);
            idx++;
        }

        // e) save updated pointer for next run
        ptr.setPointer(idx);
        pointerRepo.save(ptr);
    }

}


