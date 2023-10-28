package org.jeugenedev.simbir.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.Rent;
import org.jeugenedev.simbir.entity.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

@Tag(name = "rent-controller")
@RepositoryRestResource(collectionResourceRel = "rents", path = "rents")
public interface RentRepository extends PagingAndSortingRepository<Rent, UUID>, JpaRepository<Rent, UUID> {
    @RestResource(exported = false)
    Page<Rent> findByRenter(Account renter, Pageable pageable);
    @RestResource(exported = false)
    Page<Rent> findByTransport(Transport transport, Pageable pageable);
    @RestResource(exported = false)
    @Override
    <S extends Rent> S save(S entity);
    @RestResource(exported = false)
    @Override
    void delete(Rent entity);
}
