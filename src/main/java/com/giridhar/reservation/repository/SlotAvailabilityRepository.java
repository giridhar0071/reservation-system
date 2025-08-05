// src/main/java/com/giridhar/reservation/repository/SlotAvailabilityRepository.java
package com.giridhar.reservation.repository;

import com.giridhar.reservation.model.SlotAvailability;
import com.giridhar.reservation.model.SlotAvailability.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SlotAvailabilityRepository extends JpaRepository<SlotAvailability, UUID> {

    /**
     * Lookup a single slot by its location, the date, and the slot period.
     */
    Optional<SlotAvailability> findByLocationIdAndDateAndSlot(
            UUID locationId,
            LocalDate date,
            Slot slot
    );

    /**
     * Find all future slots that still have remaining capacity.
     */
    List<SlotAvailability> findByDateAfterAndCapacityGreaterThan(
            LocalDate date,
            int capacity
    );

    /**
     * Find all slots for a given location on a specific date.
     */
    List<SlotAvailability> findByLocationIdAndDate(
            UUID locationId,
            LocalDate date
    );

    /**
     * Sum total capacity (across all dates ≥ today) for that location.
     */
    @Query("SELECT COALESCE(SUM(s.capacity),0) "
            + "FROM SlotAvailability s "
            + "WHERE s.location.id = :locationId "
            + "  AND s.date >= CURRENT_DATE")
    long sumCapacityForLocation(@Param("locationId") UUID locationId);

    Optional<SlotAvailability> findFirstByLocationIdAndDateAfterAndCapacityGreaterThanOrderByDateAsc(
            UUID locationId,
            LocalDate fromDate,
            int minFreeCapacity
    );
}
