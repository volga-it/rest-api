package org.jeugenedev.simbir.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeugenedev.simbir.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "transport-controller")
@RepositoryRestResource(collectionResourceRel = "transports", path = "transports")
public interface TransportRepository extends PagingAndSortingRepository<Transport, Long>, JpaRepository<Transport, Long> {
}
