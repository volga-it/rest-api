package org.jeugenedev.simbir.repository;

import org.jeugenedev.simbir.entity.BannedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.sql.Timestamp;
import java.util.UUID;

@RestResource(exported = false)
public interface BannedTokenRepository extends JpaRepository<BannedToken, UUID> {
    void deleteByTimeLessThan(Timestamp time);
    boolean existsByTokenBase64Payload(String payload);
}
