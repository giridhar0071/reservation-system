package com.giridhar.reservation.repository;

import com.giridhar.reservation.model.Appointment;
import com.giridhar.reservation.model.SlotAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    /**
     * Count how many appointments have been made for the given slot.
     */
    long countBySlotAvailability(SlotAvailability slotAvailability);

    /**
     * Count how many appointments have been made at a given location.
     */
    long countBySlotAvailability_LocationId(UUID locationId);




}
