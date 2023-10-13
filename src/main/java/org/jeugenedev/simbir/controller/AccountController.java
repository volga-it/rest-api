package org.jeugenedev.simbir.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.model.AccountModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@Schema(type = "account-entity-controller")
public class AccountController {
    private final AccountModel accountModel;

    public AccountController(AccountModel accountModel) {
        this.accountModel = accountModel;
    }

    @GetMapping("/me")
    public Account me() {
        return accountModel.me();
    }
}
