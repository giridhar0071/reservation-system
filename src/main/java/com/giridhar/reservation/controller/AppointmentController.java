package com.giridhar.reservation.controller;

import com.giridhar.reservation.dto.AppointmentDTO;
import com.giridhar.reservation.dto.BookAppointmentRequest;
import com.giridhar.reservation.model.Appointment;
import com.giridhar.reservation.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    @ExceptionHandler
    @ResponseStatus
    public Map<String, Object> handleSlotFull(com.giridhar.reservation.Exception.SlotFullException ex) {
        return Map.of(
                "message", ex.getMessage(),
                "waitingEntryId", ex.getWaitingEntryId()
        );
    }

    @PostMapping
    public AppointmentDTO book(@RequestBody BookAppointmentRequest req) {
        Appointment a = appointmentService.book(req);
        return AppointmentDTO.from(a);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable UUID id) {
        appointmentService.cancel(id);
    }
}
