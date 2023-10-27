package org.jeugenedev.simbir.controller;

import org.jeugenedev.simbir.entity.Rent;
import org.jeugenedev.simbir.entity.Transport;
import org.jeugenedev.simbir.model.RentModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
