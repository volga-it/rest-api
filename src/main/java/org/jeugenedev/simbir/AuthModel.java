package org.jeugenedev.simbir;

import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.exceptions.AccountNotFoundException;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.jeugenedev.simbir.utils.JWTUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class AuthModel {
    private final JWTUtils jwtUtils;
    private final AccountRepository accountRepository;

    public AuthModel(JWTUtils jwtUtils, AccountRepository accountRepository) {
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
    }

    public Map<String, String> gen(String username, String password) {
        Account account = accountRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        if(!account.getPassword().equals(password)) {
            return Collections.emptyMap();
        }
        String token = jwtUtils.generateToken(username, password, account.getRole().name());
        return Collections.singletonMap("token", token);
    }
}
