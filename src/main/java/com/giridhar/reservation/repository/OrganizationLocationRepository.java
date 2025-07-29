package com.giridhar.reservation.repository;
import com.giridhar.reservation.model.OrganizationLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrganizationLocationRepository extends JpaRepository<OrganizationLocation, UUID> {

    /**
     * Find all locations within the given radius (in meters) of the reference point.
     */
    @Query(
            value = """
        SELECT * FROM organization_location ol
         WHERE (6371000 * 2 * ASIN(
                 SQRT(
                   POWER(SIN(RADIANS(ol.lat - :lat) / 2), 2)
                   + COS(RADIANS(:lat)) * COS(RADIANS(ol.lat))
                     * POWER(SIN(RADIANS(ol.lon - :lon) / 2), 2)
                 )
               )) <= :radius
        """,
            nativeQuery = true
    )
    List<OrganizationLocation> findWithinRadius(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") double radius
    );

    /**
     * Find the single nearest location to the reference point.
     */
    @Query(
            value = """
        SELECT * FROM organization_location ol
        ORDER BY (6371000 * 2 * ASIN(
                  SQRT(
                    POWER(SIN(RADIANS(ol.lat - :lat) / 2), 2)
                    + COS(RADIANS(:lat)) * COS(RADIANS(ol.lat))
                      * POWER(SIN(RADIANS(ol.lon - :lon) / 2), 2)
                  )
                )) ASC
        LIMIT 1
        """,
            nativeQuery = true
    )
    OrganizationLocation findNearest(
            @Param("lat") double lat,
            @Param("lon") double lon
    );
}
