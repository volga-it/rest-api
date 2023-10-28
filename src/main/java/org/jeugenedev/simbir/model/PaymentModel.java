package org.jeugenedev.simbir.model;

import jakarta.transaction.Transactional;
import org.jeugenedev.simbir.configuration.SecurityConfiguration;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.Payment;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.jeugenedev.simbir.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PaymentModel {
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    private final BigDecimal AMOUNT_ADDED = BigDecimal.valueOf(250_000);

    public PaymentModel(AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    public HttpStatus hesoyam(Account account) {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId() != account.getId() && !user.getAuthorities().contains(new SimpleGrantedAuthority(Account.Role.ROLE_ADMIN.name())))
            return HttpStatus.FORBIDDEN;

        account.setBalance(account.getBalance() != null ? account.getBalance().add(AMOUNT_ADDED) : AMOUNT_ADDED.abs());
        accountRepository.save(account);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus close(Payment payment) {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findById(user.getId()).orElseThrow();
        BigDecimal beforePay = account.getBalance().subtract(payment.getAmount());
        if (beforePay.doubleValue() < 0 || payment.isDone() ||
                payment.getPayer().getId() != account.getId()) return HttpStatus.LOCKED;

        account.setBalance(beforePay);
        payment.setDone(true);
        accountRepository.save(account);
        paymentRepository.save(payment);
        return HttpStatus.OK;
    }
}
