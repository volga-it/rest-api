package org.jeugenedev.simbir.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeugenedev.simbir.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "rent-controller")
@RepositoryRestResource(collectionResourceRel = "rents", path = "rents")
public interface RentRepository extends PagingAndSortingRepository<Rent, Long>, JpaRepository<Rent, Long> {
}
