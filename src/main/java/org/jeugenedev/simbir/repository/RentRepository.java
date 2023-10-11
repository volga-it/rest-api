package org.jeugenedev.simbir.repository;

import org.jeugenedev.simbir.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface RentRepository extends PagingAndSortingRepository<Rent, Long>, JpaRepository<Rent, Long> {
}
