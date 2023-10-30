package org.jeugenedev.simbir.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.excerpt.AccountExcerpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@Tag(name = "account-controller")
@RepositoryRestResource(excerptProjection = AccountExcerpt.class, collectionResourceRel = "accounts", path = "accounts")
public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, JpaRepository<Account, Long> {
    @RestResource(exported = false)
    Optional<Account> findByUsername(String username);
    @RestResource(exported = false)
    @Override
    <S extends Account> S save(S entity);
}
