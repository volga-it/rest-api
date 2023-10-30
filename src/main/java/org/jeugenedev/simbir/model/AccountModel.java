package org.jeugenedev.simbir.model;

import org.jeugenedev.simbir.configuration.SecurityConfiguration;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.exceptions.AccountNotFoundException;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.jeugenedev.simbir.utils.CryptoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class AccountModel {
    private final AccountRepository accountRepository;
    private final CryptoUtils cryptoUtils;

    public AccountModel(AccountRepository accountRepository, CryptoUtils cryptoUtils) {
        this.accountRepository = accountRepository;
        this.cryptoUtils = cryptoUtils;
    }

    public Account me() {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return accountRepository.findByUsername(user.getUsername()).orElseThrow(AccountNotFoundException::new);
    }

    public Account updateMe(Map<String, String> update) {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(user.getUsername()).orElseThrow();
        account.matchUpdate(update);
        return accountRepository.save(account);
    }

    private void encryptPasswordAccount(org.jeugenedev.simbir.dto.Account account) {
        account.setPassword(cryptoUtils.encrypt(account.getPassword()));
    }

    private Account updAcc(Account originalAccount, org.jeugenedev.simbir.dto.Account updateAccount) {
        encryptPasswordAccount(updateAccount);
        if(updateAccount.getUsername() != null) {
            originalAccount.setUsername(updateAccount.getUsername());
        }
        if(updateAccount.getPassword() != null) {
            originalAccount.setPassword(cryptoUtils.encrypt(updateAccount.getPassword()));
        }
        if(updateAccount.getBalance() != null) {
            originalAccount.setBalance(updateAccount.getBalance());
        }
        if(updateAccount.getRole() != null) {
            originalAccount.setRole(updateAccount.getRole());
        }

        originalAccount.setBanned(updateAccount.isBanned());
        return originalAccount;
    }

    public HttpStatus updateAccount(Account originalAccount, org.jeugenedev.simbir.dto.Account account) {
        accountRepository.save(updAcc(originalAccount, account));
        return HttpStatus.OK;
    }

    public HttpStatus patchAccount(Account originalAccount, org.jeugenedev.simbir.dto.Account account) {
        accountRepository.save(updAcc(originalAccount, account));
        return HttpStatus.OK;
    }

    public HttpStatus createUser(org.jeugenedev.simbir.dto.Account account) {
        encryptPasswordAccount(account);
        Account acc = new Account(account.getUsername(), account.getPassword(), account.isBanned(),
                account.getBalance() == null ? BigDecimal.ZERO : account.getBalance(),
                account.getRole() == null ? Account.Role.ROLE_USER : account.getRole());
        accountRepository.save(acc);
        return HttpStatus.OK;
    }
}
