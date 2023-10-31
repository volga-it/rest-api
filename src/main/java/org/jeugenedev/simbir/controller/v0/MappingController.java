package org.jeugenedev.simbir.controller.v0;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MappingController {
    @GetMapping("/api/Account/Me")
    public String accountMe() {
        return "forward:/accounts/me";
    }

    @PostMapping("/api/Account/Update")
    public String accountUpdateMe() {
        return "forward:/accounts/me/update";
    }

    @PostMapping("/api/Account/SignUp")
    public String accountCreate() {
        return "forward:/accounts";
    }

    @PostMapping("/api/Account/SignIn")
    public String authToken() {
        return "forward:/auth/token";
    }

    @PostMapping("/api/Account/SignOut")
    public String authDeny(@RequestHeader String authorization) {
        return "forward:/auth/deny/" + authorization.split(" ")[1];
    }



    @GetMapping("/api/Transport/{id}")
    public String transportGet(@PathVariable long id) {
        return "forward:/transports/" + id;
    }

    @PostMapping("/api/Transport")
    public String transportCreate() {
        return "forward:/transports";
    }

    @PutMapping("/api/Transport/{id}")
    public String transportChange(@PathVariable long id) {
        return "forward:/transports/" + id;
    }

    @DeleteMapping("/api/Transport/{id}")
    public String transportDelete(@PathVariable long id) {
        return "forward:/transports/" + id;
    }



    @GetMapping("/api/Rent/Transport")
    public String transportSearch() {
        return "forward:/rents/transport";
    }

    @GetMapping("/api/Rent/{rentId}")
    public String rentGet(@PathVariable long rentId) {
        return "forward:/rents/" + rentId;
    }

    @GetMapping("/api/Rent/MyHistory")
    public String rentHistoryMe() {
        return "forward:/rents/history/me";
    }

    @GetMapping("/api/Rent/TransportHistory/{transportId}")
    public String rentTransportHistory(@PathVariable long transportId) {
        return "forward:/rents/history/transport/" + transportId;
    }

    @PostMapping("/api/Rent/New/{transportId}")
    public String rentTransport(@PathVariable long transportId) {
        return "forward:/rents/transport/" + transportId;
    }

    @PostMapping("/api/Rent/End/{rentId}")
    public String rentEnd(@PathVariable long rentId) {
        return "forward:/rents/close/" + rentId;
    }



    @PostMapping("/api/Payment/Hesoyam/{accountId}")
    public String payment(@PathVariable long accountId) {
        return "forward:/payments/hesoyam/" + accountId;
    }



    @GetMapping("/api/Admin/Account")
    public String adminAccountGet() {
        return "forward:/accounts";
    }

    @GetMapping("/api/Admin/Account/{id}")
    public String adminAccountById(@PathVariable long id) {
        return "forward:/accounts/" + id;
    }

    @PostMapping("/api/Admin/Account")
    public String adminAccountCreate() {
        return "forward:/accounts";
    }

    @PutMapping("/api/Admin/Account/{id}")
    public String adminAccountChange(@PathVariable long id) {
        return "forward:/accounts/" + id;
    }

    @DeleteMapping("/api/Admin/Account/{id}")
    public String adminAccountDelete(@PathVariable long id) {
        return "forward:/accounts/" + id;
    }



    @GetMapping("/api/Admin/Transport")
    public String adminTransportGet() {
        return "forward:/transports";
    }

    @GetMapping("/api/Admin/Transport/{id}")
    public String adminTransportById(@PathVariable long id) {
        return "forward:/transports/" + id;
    }

    @PostMapping("/api/Admin/Transport")
    public String adminTransportCreate() {
        return "forward:/transports";
    }

    @PutMapping("/api/Admin/Transport/{id}")
    public String adminTransportChange(@PathVariable long id) {
        return "forward:/transports/" + id;
    }

    @DeleteMapping("/api/Admin/Transport/{id}")
    public String adminTransportDelete(@PathVariable long id) {
        return "forward:/transports" + id;
    }



    @GetMapping("/api/Admin/Rent/{rentId}")
    public String adminRentGetById(@PathVariable long rentId) {
        return "forward:/rents/" + rentId;
    }

    @GetMapping("/api/Admin/TransportHistory/{transportId}")
    public String adminRentTransport(@PathVariable long transportId) {
        return "forward:/rents/history/transport/" + transportId;
    }

    @PostMapping("/api/Admin/Rent/End/{rentId}")
    public String adminRentEnd(@PathVariable long rentId) {
        return "forward:/rents/close/" + rentId;
    }



    /*
    * Не имплементировано в счет отсутствия необходимости
    */

    @GetMapping("/api/Admin/UserHistory/{userId}")
    public HttpStatus adminRentUserHistory(@PathVariable long userId) {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    @PostMapping("/api/Admin/Rent")
    public HttpStatus adminRentCreate() {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    @PutMapping("/api/Admin/Rent/{id}")
    public HttpStatus adminRentChange(@PathVariable long id) {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    @DeleteMapping("/api/Admin/Rent/{rentId}")
    public HttpStatus adminRentDelete(@PathVariable long rentId) {
        return HttpStatus.NOT_IMPLEMENTED;
    }
}
