package org.jeugenedev.simbir.repository;

import org.jeugenedev.simbir.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface TransportRepository extends PagingAndSortingRepository<Transport, Long>, JpaRepository<Transport, Long> {
}
