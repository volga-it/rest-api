package org.jeugenedev.simbir.model;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.BannedToken;
import org.jeugenedev.simbir.exceptions.AccountNotFoundException;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.jeugenedev.simbir.repository.BannedTokenRepository;
import org.jeugenedev.simbir.utils.JWTUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.UUID;

@Component
public class AuthModel {
    private final JWTUtils jwtUtils;
    private final AccountRepository accountRepository;
    private final BannedTokenRepository bannedTokenRepository;

    public AuthModel(JWTUtils jwtUtils, AccountRepository accountRepository, BannedTokenRepository bannedTokenRepository) {
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
        this.bannedTokenRepository = bannedTokenRepository;
    }

    public AccountToken gen(String username, String password) {
        Account account = accountRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        String token = jwtUtils.generateToken(account.getId(), username, account.getRole().name());
        boolean predicate = account.getPassword().equals(password);
        return new AccountToken(account.getId(), predicate ? token : "", predicate);
    }

    public HttpStatus deny(String token) {
        try {
            String encodedPayload = Base64.getUrlEncoder().encodeToString(jwtUtils.getPayload(token).getBytes(StandardCharsets.UTF_8));
            bannedTokenRepository.save(new BannedToken(UUID.randomUUID(), encodedPayload, new Timestamp(System.currentTimeMillis())));
            return HttpStatus.OK;
        } catch (JWTVerificationException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public record AccountToken(long accId, String token, boolean auth) {
    }
}
