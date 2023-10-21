package org.jeugenedev.simbir.service;

import jakarta.transaction.Transactional;
import org.jeugenedev.simbir.repository.BannedTokenRepository;
import org.jeugenedev.simbir.utils.JWTUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TokenLifeService {
    private static final int MINUTES_10 = 600000;

    private final BannedTokenRepository bannedTokenRepository;
    private final long expired;

    public TokenLifeService(BannedTokenRepository bannedTokenRepository) {
        this.bannedTokenRepository = bannedTokenRepository;
        this.expired = JWTUtils.getTokenExpired();
    }

    @Transactional
    @Scheduled(fixedDelay = MINUTES_10)
    public void deleteExpired() {
        Logger
                .getLogger(getClass().getName())
                .log(Level.INFO, "The BannedToken table has been cleared of expired tokens");
        bannedTokenRepository.deleteByTimeLessThan(new Timestamp(System.currentTimeMillis() - this.expired));
    }
}
