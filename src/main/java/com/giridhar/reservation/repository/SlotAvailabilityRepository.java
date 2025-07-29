package com.giridhar.reservation.repository;



import com.giridhar.reservation.model.OrganizationLocation;
import com.giridhar.reservation.model.SlotAvailability;
import com.giridhar.reservation.model.SlotAvailability.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



import com.giridhar.reservation.model.SlotAvailability;
import com.giridhar.reservation.model.SlotAvailability.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SlotAvailabilityRepository extends JpaRepository<SlotAvailability, UUID> {

    List<SlotAvailability> findByLocationIdAndDate(UUID locationId, LocalDate date);

    // Finds one slot by location, date and slot enum
    Optional<SlotAvailability> findByLocationIdAndDateAndSlot(
            UUID locationId,
            LocalDate date,
            Slot slot
    );
}

