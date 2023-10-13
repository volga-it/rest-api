package org.jeugenedev.simbir.model;

import org.jeugenedev.simbir.configuration.SecurityConfiguration;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.exceptions.AccountNotFoundException;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccountModel {
    private final AccountRepository accountRepository;

    public AccountModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account me() {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return accountRepository.findByUsername(user.getUsername()).orElseThrow(AccountNotFoundException::new);
    }
}
