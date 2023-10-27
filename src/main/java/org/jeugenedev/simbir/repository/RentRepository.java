package org.jeugenedev.simbir.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@Tag(name = "rent-controller")
@RepositoryRestResource(collectionResourceRel = "rents", path = "rents")
public interface RentRepository extends PagingAndSortingRepository<Rent, Long>, JpaRepository<Rent, Long> {
    @RestResource(exported = false)
    Page<Rent> findByRenter(Account renter, Pageable pageable);
}
