package org.jeugenedev.simbir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.Payment;
import org.jeugenedev.simbir.model.PaymentModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/payments")
@RestController
public class PaymentController {
    private final PaymentModel paymentModel;

    public PaymentController(PaymentModel paymentModel) {
        this.paymentModel = paymentModel;
    }

    @Operation(
            description = "Пополняет аккаунт пользователя на 250.000 денежных единиц",
            parameters = @Parameter(
                    name = "account",
                    description = "Идентификатор аккаунта пользователя",
                    schema = @Schema(type = "int64")
            ),
            summary = "Авторизованные пользователи"
    )
    @PostMapping("/hesoyam/{account}")
    public HttpStatus hesoyam(@PathVariable Account account) {
        return paymentModel.hesoyam(account);
    }

    @Operation(
            description = "Завершает оплату неоплаченного счета текущего аккаунта пользователя",
            parameters = @Parameter(
                    name = "payment",
                    description = "Идентификатор счета платежа",
                    schema = @Schema(type = "UUID")
            ),
            summary = "Авторизованные пользователи"
    )
    @PostMapping("/close/{payment}")
    public HttpStatus close(@PathVariable Payment payment) {
        return paymentModel.close(payment);
    }
}
