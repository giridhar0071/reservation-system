package com.giridhar.reservation.dto;


import com.giridhar.reservation.model.SlotAvailability.Slot;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Payload for creating a new slot availability.
 */
public class CreateSlotRequest {
    private UUID locationId;
    private LocalDate date;
    private Slot slot;
    private int capacity;

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}

