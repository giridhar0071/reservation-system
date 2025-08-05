package com.giridhar.reservation.model;


import jakarta.persistence.*;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "slot_availability")
public class SlotAvailability {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private OrganizationLocation location;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Slot slot;

    private int capacity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrganizationLocation getLocation() {
        return location;
    }

    public void setLocation(OrganizationLocation location) {
        this.location = location;
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

    public enum Slot {
        MORNING, AFTERNOON, EVENING
    }



}
