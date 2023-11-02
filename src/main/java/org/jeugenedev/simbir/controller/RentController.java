package org.jeugenedev.simbir.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            description = "Возвращает список объектов транспорта, удовлетворяющий условиям фильтра радиуса и типа",
            parameters = {
                    @Parameter(name = "lat", description = "lat центра круга поиска"),
                    @Parameter(name = "lon", description = "lan центра круга поиска"),
                    @Parameter(name = "radius", description = "Радиус центра круга поиска"),
                    @Parameter(name = "type", description = "Тип транспорта"),
                    @Parameter(name = "page", description = "Страница общего результата"),
                    @Parameter(name = "count", description = "Количество элементов в одной странице page")
            },
            summary = "Весь интернет"
    )
    @GetMapping("/transport")
    public List<Transport> transport(@RequestParam double lat,
                                     @RequestParam double lon,
                                     @RequestParam double radius,
                                     @RequestParam Transport.Type type,
                                     @RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.transport(lat, lon, radius, type, page, count);
    }

    @Operation(
            description = "Возвращает историю аренд текущего аккаунта пользователя",
            parameters = {
                    @Parameter(name = "page", description = "Страница общего результата"),
                    @Parameter(name = "count", description = "Количество элементов в одной странице page")
            },
            summary = "Авторизованные пользователи"
    )
    @GetMapping("/history/me")
    public List<Rent> historyMe(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.myHistory(page, count);
    }

    @Operation(
            description = "Возвращает историю аренд для текущего транспорта",
            parameters = {
                    @Parameter(
                            name = "transport",
                            description = "Идентификатор исходного транспорта",
                            schema = @Schema(type = "int64")
                    ),
                    @Parameter(name = "page", description = "Страница общего результата"),
                    @Parameter(name = "count", description = "Количество элементов в одной странице page")
            },
            summary = "Авторизованные пользователи"
    )
    @GetMapping("/history/transport/{transport}")
    public List<Rent> transportHistory(@PathVariable Transport transport,
                                            @RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.transportHistory(transport, page, count);
    }

    @Operation(
            description = "Возвращает историю аренд аккаунта пользователя",
            parameters = {
                    @Parameter(
                            name = "account",
                            description = "Идетификатор аккаунта пользователя",
                            schema = @Schema(type = "int64")
                    ),
                    @Parameter(name = "page", description = "Страница общего результата"),
                    @Parameter(name = "count", description = "Количество элементов в одной странице page")
            },
            summary = "Авторизованные пользователи"
    )
    @GetMapping("/history/account/{account}")
    public List<Rent> accountRentHistory(@PathVariable Account account,
                                         @RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int count) {
        return this.rentModel.accountRentHistory(account, page, count);
    }

    @Operation(
            description = "Начинает аренду указанного транспорта текущим пользователем",
            parameters = {
                    @Parameter(
                            name = "transport",
                            description = "Идентификатор исходного транспорта",
                            schema = @Schema(type = "int64")
                    ),
                    @Parameter(name = "rentType", description = "Тип аренды транспорта")
            },
            summary = "Авторизованные пользователи"
    )
    @PostMapping("/transport/{transport}")
    public HttpStatus rentTransport(@PathVariable Transport transport,
                                    @RequestParam(name = "rent_type") Rent.Type rentType) {
        return this.rentModel.rentTransport(transport, rentType);
    }

    @Operation(
            description = "Завершает указанную аренду",
            parameters = {
                    @Parameter(
                            name = "rent",
                            description = "Идентификатор аренды",
                            schema = @Schema(type = "UUID")
                    ),
                    @Parameter(name = "lat", description = "lat центра круга поиска"),
                    @Parameter(name = "lon", description = "lan центра круга поиска")
            },
            summary = "Только аккаунт пользователя, который начал аренду"
    )
    @PostMapping("/close/{rent}")
    public HttpStatus rentClose(@PathVariable Rent rent,
                                @RequestParam double lat,
                                @RequestParam double lon) {
        return this.rentModel.rentClose(rent, lat, lon);
    }
}
