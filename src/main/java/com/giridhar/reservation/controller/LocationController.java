package com.giridhar.reservation.controller;

import com.giridhar.reservation.dto.LocationDTO;
import com.giridhar.reservation.service.GeoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final GeoService geoService;

    public LocationController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/nearby")
    public List<LocationDTO> nearby(
            @RequestParam String city,
            @RequestParam String zip
    ) {
        return geoService.findNearbyOrNearest(city, zip).stream()
                .map(LocationDTO::from)
                .toList();
    }
}
