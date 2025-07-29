package com.giridhar.reservation.dto;


import com.giridhar.reservation.model.Appointment;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for returning appointment details.
 */
public record AppointmentDTO(
        UUID id,
        UUID patientId,
        UUID slotId,
        UUID repId,
        Instant bookedAt
) {
    public static AppointmentDTO from(Appointment a) {
        return new AppointmentDTO(
                a.getId(),
                a.getPatient().getId(),
                a.getSlotAvailability().getId(),
                a.getRep().getId(),
                a.getBookedAt()
        );
    }
}
