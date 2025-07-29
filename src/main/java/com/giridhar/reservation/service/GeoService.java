package com.giridhar.reservation.service;


import com.giridhar.reservation.model.OrganizationLocation;
import com.giridhar.reservation.model.PostalCode;
import com.giridhar.reservation.repository.OrganizationLocationRepository;
import com.giridhar.reservation.repository.PostalCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeoService {
    private static final double RADIUS_METERS = 8045; // ≈5 miles

    private final PostalCodeRepository postalRepo;
    private final OrganizationLocationRepository locRepo;

    public GeoService(PostalCodeRepository postalRepo,
                      OrganizationLocationRepository locRepo) {
        this.postalRepo = postalRepo;
        this.locRepo    = locRepo;
    }

    @Transactional(readOnly = true)
    public List<OrganizationLocation> findNearbyOrNearest(String city, String zip) {
        PostalCode pc = postalRepo.findById(zip)
                .orElseThrow(() -> new IllegalArgumentException("Unknown ZIP: " + zip));
        // 1) Try radius
        List<OrganizationLocation> within = locRepo.findWithinRadius(pc.getLat(), pc.getLon(), RADIUS_METERS);
        if (!within.isEmpty()) return within;
        // 2) Fallback to one nearest
        return List.of(locRepo.findNearest(pc.getLat(), pc.getLon()));
    }
}

