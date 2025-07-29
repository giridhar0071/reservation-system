package com.giridhar.reservation.dto;

import java.time.LocalDate;
import java.util.UUID;
import com.giridhar.reservation.model.SlotAvailability.Slot;

public class BookAppointmentRequest {
    private UUID patientId;
    private UUID repId;

    private String city;
    private String zip;
    private LocalDate date;
    private Slot slot;

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getRepId() {
        return repId;
    }

    public void setRepId(UUID repId) {
        this.repId = repId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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
}