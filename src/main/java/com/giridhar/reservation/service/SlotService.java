package com.giridhar.reservation.service;

import com.giridhar.reservation.model.OrganizationLocation;
import com.giridhar.reservation.model.SlotAvailability;
import com.giridhar.reservation.model.SlotAvailability.Slot;
import com.giridhar.reservation.repository.OrganizationLocationRepository;
import com.giridhar.reservation.repository.SlotAvailabilityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SlotService {
    private final SlotAvailabilityRepository  slotRepo;
    private final OrganizationLocationRepository locRepo;

    public SlotService(
            SlotAvailabilityRepository slotRepo,
            OrganizationLocationRepository locRepo
    ) {
        this.slotRepo = slotRepo;
        this.locRepo  = locRepo;
    }

    /**
     * Create a new slot for a given location, date and slot type.
     * Throws if the location doesn't exist, the slot already exists,
     * or capacity is non-positive.
     */
    public SlotAvailability createSlot(
            UUID locationId,
            LocalDate date,
            Slot slot,
            int capacity
    ) {
        // 1) Validate location exists
        OrganizationLocation loc = locRepo.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "OrganizationLocation not found: " + locationId));

        // 2) Prevent duplicates
        Optional<SlotAvailability> existing =
                slotRepo.findByLocationIdAndDateAndSlot(locationId, date, slot);
        if (existing.isPresent()) {
            throw new IllegalStateException(
                    "Slot already exists for location=" + locationId +
                            ", date=" + date + ", slot=" + slot);
        }

        // 3) Validate capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }

        // 4) Build and save
        SlotAvailability s = new SlotAvailability();
        s.setLocation(loc);
        s.setDate(date);
        s.setSlot(slot);
        s.setCapacity(capacity);
        return slotRepo.save(s);
    }

    /**
     * List all slots for a given location and date.
     * Throws if the location doesn't exist.
     */
    @Transactional(readOnly = true)
    public List<SlotAvailability> listByLocationAndDate(
            UUID locationId,
            LocalDate date
    ) {
        // Ensure the location is valid
        if (!locRepo.existsById(locationId)) {
            throw new EntityNotFoundException(
                    "OrganizationLocation not found: " + locationId);
        }
        return slotRepo.findByLocationIdAndDate(locationId, date);
    }

    /**
     * Delete a slot by its UUID.
     * Throws if the slot doesn't exist.
     */
    public void deleteSlot(UUID slotId) {
        if (!slotRepo.existsById(slotId)) {
            throw new EntityNotFoundException(
                    "SlotAvailability not found: " + slotId);
        }
        slotRepo.deleteById(slotId);
    }

    /**
     * Optional helper: update capacity of an existing slot.
     */
    public SlotAvailability updateCapacity(UUID slotId, int newCapacity) {
        SlotAvailability s = slotRepo.findById(slotId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SlotAvailability not found: " + slotId));
        if (newCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        s.setCapacity(newCapacity);
        return slotRepo.save(s);
    }
}
