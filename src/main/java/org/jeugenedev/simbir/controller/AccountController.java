package org.jeugenedev.simbir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(
            description = "Возвращает текущего пользователя",
            summary = "Авторизованные пользователи"
    )
    @GetMapping("/me")
    public Account me() {
        return accountModel.me();
    }

    @Operation(
            description = "Обновляет текущего пользователя",
            summary = "Авторизованные пользователи"
    )
    @PostMapping("/me/update")
    public Account updateMe(@RequestBody Map<String, String> update) {
        return accountModel.updateMe(update);
    }

    @Operation(
            description = "Создает аккаунт нового пользователя",
            summary = "Только администраторы"
    )
    @PostMapping
    public HttpStatus createUser(@RequestBody org.jeugenedev.simbir.dto.Account account) {
        return accountModel.createUser(account);
    }

    @Operation(
            description = "Обновляет аккаунт пользователя",
            parameters = @Parameter(
                    name = "account",
                    description = "Идетификатор аккаунта пользователя",
                    schema = @Schema(type = "int64")
            ),
            summary = "Только администраторы"
    )
    @PutMapping("/{account}")
    public HttpStatus updateAccount(@PathVariable("account") Account originalAccount,
                                    @RequestBody org.jeugenedev.simbir.dto.Account account) {
        return accountModel.updateAccount(originalAccount, account);
    }

    @Operation(
            description = "Изменяет аккаунт пользователя",
            parameters = @Parameter(
                    name = "account",
                    description = "Идетификатор аккаунта пользователя",
                    schema = @Schema(type = "int64")
            ),
            summary = "Только администраторы"
    )
    @PatchMapping("/{account}")
    public HttpStatus patchAccount(@PathVariable("account") Account originalAccount,
                                   @RequestBody org.jeugenedev.simbir.dto.Account account) {
        return accountModel.patchAccount(originalAccount, account);
    }
}
