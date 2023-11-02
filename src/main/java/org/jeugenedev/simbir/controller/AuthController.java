package org.jeugenedev.simbir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.jeugenedev.simbir.model.AuthModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthModel authModel;

    public AuthController(AuthModel authModel) {
        this.authModel = authModel;
    }

    @Operation(
            description = "Возвращает JWT токен аккаунта",
            parameters = {
                    @Parameter(
                            name = "username",
                            description = "Никнейм аккаунта пользователя"
                    ),
                    @Parameter(
                            name = "password",
                            description = "Пароль аккаунта пользователя"
                    )
            },
            summary = "Весь интернет"
    )
    @PostMapping("/token")
    public AuthModel.AccountToken gen(@RequestParam String username, @RequestParam String password) {
        return authModel.gen(username, password);
    }

    @Operation(
            description = "Блокирует JWT токен",
            parameters = @Parameter(name = "token", description = "Токен аккаунта пользователя"),
            summary = "Текущий токен или доступ администратора"
    )
    @PostMapping("/deny/{token}")
    public HttpStatus deny(@PathVariable String token) {
        return authModel.deny(token);
    }
}
