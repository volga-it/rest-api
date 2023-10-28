package org.jeugenedev.simbir.controller;

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

    @PostMapping("/hesoyam/{account}")
    public HttpStatus hesoyam(@PathVariable Account account) {
        return paymentModel.hesoyam(account);
    }

    @PostMapping("/close/{payment}")
    public HttpStatus close(@PathVariable Payment payment) {
        return paymentModel.close(payment);
    }
}
