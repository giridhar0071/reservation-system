package com.giridhar.reservation.repository;

import com.giridhar.reservation.model.Appointment;
import com.giridhar.reservation.model.SlotAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    /**
     * Count how many appointments have been made for a given slot.
     */
    long countBySlotAvailability(SlotAvailability slotAvailability);
}
