package com.giridhar.reservation.model;

import com.giridhar.reservation.model.OrganizationLocation;
import com.giridhar.reservation.model.Patient;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "waiting_list")
public class WaitingListEntry {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private OrganizationLocation location;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "WAITING";

    protected WaitingListEntry() {}

    public WaitingListEntry(Patient patient,
                            OrganizationLocation location,
                            Instant requestedAt) {
        this.patient     = patient;
        this.location    = location;
        this.requestedAt = requestedAt;
    }

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

    public OrganizationLocation getLocation() {
        return location;
    }

    public void setLocation(OrganizationLocation location) {
        this.location = location;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
