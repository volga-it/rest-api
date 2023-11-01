package org.jeugenedev.simbir.controller;

import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.Rent;
import org.jeugenedev.simbir.entity.Transport;
import org.jeugenedev.simbir.model.RentModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rents")
@RestController
public class RentController {
    private final RentModel rentModel;

    public RentController(RentModel rentModel) {
        this.rentModel = rentModel;
    }

    @GetMapping("/transport")
    public List<Transport> transport(@RequestParam double lat,
                                     @RequestParam double lon,
                                     @RequestParam double radius,
                                     @RequestParam Transport.Type type,
                                     @RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.transport(lat, lon, radius, type, page, count);
    }

    @GetMapping("/history/me")
    public List<Rent> historyMe(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.myHistory(page, count);
    }

    @GetMapping("/history/transport/{transport}")
    public List<Rent> transportHistory(@PathVariable Transport transport,
                                            @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.transportHistory(transport, page, count);
    }

    @GetMapping("/history/account/{account}")
    public List<Rent> accountRentHistory(@PathVariable Account account,
                                         @RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.accountRentHistory(account, page, count);
    }

    @PostMapping("/transport/{transport}")
    public HttpStatus rentTransport(@PathVariable Transport transport,
                                    @RequestParam(name = "rent_type") Rent.Type rentType) {
        return this.rentModel.rentTransport(transport, rentType);
    }

    @PostMapping("/close/{rent}")
    public HttpStatus rentClose(@PathVariable Rent rent,
                                @RequestParam double lat,
                                @RequestParam double lon) {
        return this.rentModel.rentClose(rent, lat, lon);
    }
}
