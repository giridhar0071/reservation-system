package com.giridhar.reservation.service;
import com.giridhar.reservation.model.Appointment;
import com.giridhar.reservation.model.WaitingListEntry;
import com.giridhar.reservation.repository.AppointmentRepository;
import com.giridhar.reservation.repository.SlotAvailabilityRepository;
import com.giridhar.reservation.repository.WaitingListEntryRepository;
import com.giridhar.reservation.service.RepresentativeService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class WaitingListAllocatorService {

    private final WaitingListEntryRepository waitingRepo;
    private final SlotAvailabilityRepository slotRepo;
    private final AppointmentRepository apptRepo;
    private final RepresentativeService repService;

    public WaitingListAllocatorService(
            WaitingListEntryRepository waitingRepo,
            SlotAvailabilityRepository slotRepo,
            AppointmentRepository apptRepo,
            RepresentativeService repService
    ) {
        this.waitingRepo = waitingRepo;
        this.slotRepo    = slotRepo;
        this.apptRepo    = apptRepo;
        this.repService  = repService;
    }

    @Transactional
    public void allocateFromWaitingList(int batchSize) {
        List<WaitingListEntry> entries = waitingRepo.findByStatusOrderByRequestedAtAsc(
                "WAITING",
                PageRequest.of(0, batchSize)
        );


        for (var entry : entries) {
            var loc = entry.getLocation();
            var slotOpt = slotRepo
                    .findFirstByLocationIdAndDateAfterAndCapacityGreaterThanOrderByDateAsc(
                            loc.getId(),
                            LocalDate.now(),
                            0
                    );

            if (slotOpt.isPresent()) {
                var slot = slotOpt.get();
                var appt = new Appointment(
                        entry.getPatient(),
                        repService.selectDefaultRep(),
                        slot,
                        Instant.now()
                );
                apptRepo.save(appt);
                waitingRepo.delete(entry);
            }
        }
    }
}
