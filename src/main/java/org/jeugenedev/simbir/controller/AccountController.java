package org.jeugenedev.simbir.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.model.AccountModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/me/update")
    public Account updateMe(@RequestBody Map<String, String> update) {
        return accountModel.updateMe(update);
    }

    @PostMapping
    public HttpStatus createUser(@RequestBody org.jeugenedev.simbir.dto.Account account) {
        return accountModel.createUser(account);
    }

    @PutMapping("/{account}")
    public HttpStatus updateAccount(@PathVariable("account") Account originalAccount,
                                    @RequestBody org.jeugenedev.simbir.dto.Account account) {
        return accountModel.updateAccount(originalAccount, account);
    }

    @PatchMapping("/{account}")
    public HttpStatus patchAccount(@PathVariable("account") Account originalAccount,
                                   @RequestBody org.jeugenedev.simbir.dto.Account account) {
        return accountModel.patchAccount(originalAccount, account);
    }
}
