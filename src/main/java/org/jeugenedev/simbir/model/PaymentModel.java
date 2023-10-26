package org.jeugenedev.simbir.model;

import org.jeugenedev.simbir.configuration.SecurityConfiguration;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentModel {
    private final AccountRepository accountRepository;

    private final BigDecimal AMOUNT_ADDED = BigDecimal.valueOf(250_000);

    public PaymentModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public HttpStatus hesoyam(Account account) {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getId() != account.getId() && !user.getAuthorities().contains(new SimpleGrantedAuthority(Account.Role.ROLE_ADMIN.name()))) return HttpStatus.FORBIDDEN;

        account.setBalance(account.getBalance() != null ? account.getBalance().add(AMOUNT_ADDED) : AMOUNT_ADDED.abs());
        accountRepository.save(account);
        return HttpStatus.OK;
    }
}
