package org.jeugenedev.simbir.model;

import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.exceptions.AccountNotFoundException;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.jeugenedev.simbir.utils.JWTUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthModel {
    private final JWTUtils jwtUtils;
    private final AccountRepository accountRepository;

    public AuthModel(JWTUtils jwtUtils, AccountRepository accountRepository) {
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
    }

    public AccountToken gen(String username, String password) {
        Account account = accountRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        String token = jwtUtils.generateToken(username, password, Account.Role.byId(account.getRoleId()).name());
        boolean predicate = account.getPassword().equals(password);
        return new AccountToken(account.getId(), predicate ? token : "", predicate);
    }

    public record AccountToken(long accId, String token, boolean auth) {
    }
}
