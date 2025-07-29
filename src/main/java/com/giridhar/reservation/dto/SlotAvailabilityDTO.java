package com.giridhar.reservation.dto;

import com.giridhar.reservation.model.SlotAvailability;
import com.giridhar.reservation.model.SlotAvailability.Slot;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for returning slot availability details.
 */
public record SlotAvailabilityDTO(
        UUID id,
        UUID locationId,
        LocalDate date,
        Slot slot,
        int capacity
) {
    public static SlotAvailabilityDTO from(SlotAvailability s) {
        return new SlotAvailabilityDTO(
                s.getId(),
                s.getLocation().getId(),
                s.getDate(),
                s.getSlot(),
                s.getCapacity()
        );
    }
}
