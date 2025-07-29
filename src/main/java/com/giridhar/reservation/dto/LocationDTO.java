package com.giridhar.reservation.dto;


import com.giridhar.reservation.model.OrganizationLocation;
import java.util.UUID;

public record LocationDTO(
        UUID id,
        String address,
        double lat,
        double lon,
        String organizationName
) {
    public static LocationDTO from(OrganizationLocation loc) {
        return new LocationDTO(
                loc.getId(),
                loc.getAddress(),
                loc.getLat(),
                loc.getLon(),
                loc.getOrganization().getName()
        );
    }
}
