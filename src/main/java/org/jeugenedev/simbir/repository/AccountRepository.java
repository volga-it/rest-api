package org.jeugenedev.simbir.repository;

import org.jeugenedev.simbir.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RepositoryRestController
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
