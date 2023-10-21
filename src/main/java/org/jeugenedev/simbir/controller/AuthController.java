package org.jeugenedev.simbir.controller;

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

    @PostMapping("/token")
    public AuthModel.AccountToken gen(@RequestParam String username, @RequestParam String password) {
        return authModel.gen(username, password);
    }

    @PostMapping("/deny/{token}")
    public HttpStatus deny(@PathVariable String token) {
        return authModel.deny(token);
    }
}
