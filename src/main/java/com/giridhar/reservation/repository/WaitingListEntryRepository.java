package com.giridhar.reservation.repository;

import com.giridhar.reservation.model.WaitingListEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface WaitingListEntryRepository extends JpaRepository<WaitingListEntry, UUID> {
    /**
     * Count how many people are waiting for a given location.
     * We navigate through slotAvailability → location.id
     */
    long countByLocation_Id(UUID locationId);
    List<WaitingListEntry> findByStatusOrderByRequestedAtAsc(
            String status,
            Pageable pageable
    );
}
