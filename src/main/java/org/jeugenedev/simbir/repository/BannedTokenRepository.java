package org.jeugenedev.simbir.repository;

import org.jeugenedev.simbir.entity.BannedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.UUID;

public interface BannedTokenRepository extends JpaRepository<BannedToken, UUID> {
    void deleteByTimeLessThan(Timestamp time);
}
