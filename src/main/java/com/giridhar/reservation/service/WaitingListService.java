package com.giridhar.reservation.service;

import com.giridhar.reservation.model.OrganizationLocation;
import com.giridhar.reservation.model.Patient;
import com.giridhar.reservation.model.WaitingListEntry;
import com.giridhar.reservation.repository.WaitingListEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class WaitingListService {

    private final WaitingListEntryRepository waitingRepo;

    public WaitingListService(WaitingListEntryRepository waitingRepo) {
        this.waitingRepo = waitingRepo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UUID addToWaitingList(Patient patient, OrganizationLocation location) {
        WaitingListEntry entry = new WaitingListEntry(patient, location, Instant.now());
        return waitingRepo.save(entry).getId();
    }
}
