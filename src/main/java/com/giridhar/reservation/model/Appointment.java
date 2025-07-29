package com.giridhar.reservation.model;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "slot_id")
    private SlotAvailability slotAvailability;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rep_id")
    private Representative rep;

    @Column(name = "booked_at")
    private Instant bookedAt = Instant.now();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public SlotAvailability getSlotAvailability() {
        return slotAvailability;
    }

    public void setSlotAvailability(SlotAvailability slotAvailability) {
        this.slotAvailability = slotAvailability;
    }

    public Representative getRep() {
        return rep;
    }

    public void setRep(Representative rep) {
        this.rep = rep;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Instant bookedAt) {
        this.bookedAt = bookedAt;
    }
}
