package org.jeugenedev.simbir.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeugenedev.simbir.entity.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@Tag(name = "transport-controller")
@RepositoryRestResource(collectionResourceRel = "transports", path = "transports")
public interface TransportRepository extends PagingAndSortingRepository<Transport, Long>, JpaRepository<Transport, Long> {
    @RestResource(exported = false)
    @Query("from Transport where getDistance(:lat, :lon, latitude, longitude) <= :radius and (type = :type or :type = 4)")
    Page<Transport> findTransportByRadius(double lat, double lon, double radius, Transport.Type type, Pageable pageable);
}
