package com.giridhar.reservation.service;

import com.giridhar.reservation.model.Representative;
import com.giridhar.reservation.repository.RepresentativeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RepresentativeService {
    private final RepresentativeRepository repRepo;

    public RepresentativeService(RepresentativeRepository repRepo) {
        this.repRepo = repRepo;
    }

    /** Return whatever rep you want to use by default. */
    public Representative selectDefaultRep() {
        // e.g. return the first by ID, or your custom logic
        return repRepo.findAll(Sort.by("id"))
                .stream()
                .findFirst()
                .orElseThrow();
    }
}

