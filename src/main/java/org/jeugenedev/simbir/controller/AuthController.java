package org.jeugenedev.simbir.controller;

import org.jeugenedev.simbir.model.AuthModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
}
