package org.jeugenedev.simbir.model;

import jakarta.transaction.Transactional;
import org.jeugenedev.simbir.configuration.SecurityConfiguration;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.Payment;
import org.jeugenedev.simbir.entity.Rent;
import org.jeugenedev.simbir.entity.Transport;
import org.jeugenedev.simbir.repository.AccountRepository;
import org.jeugenedev.simbir.repository.PaymentRepository;
import org.jeugenedev.simbir.repository.RentRepository;
import org.jeugenedev.simbir.repository.TransportRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Component
public class RentModel {
    private final RentRepository rentRepository;
    private final TransportRepository transportRepository;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    public RentModel(RentRepository rentRepository, TransportRepository transportRepository, AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.rentRepository = rentRepository;
        this.transportRepository = transportRepository;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Transport> transport(double lat, double lon, double radius, Transport.Type type, int page, int count) {
        return transportRepository.findTransportByRadius(lat, lon, radius, type, PageRequest.of(page, count)).getContent();
    }

    public List<Rent> myHistory(int page, int count) {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return rentRepository.findByRenter(new Account(user.getId()), PageRequest.of(page, count)).getContent();
    }

    public List<Transport> transportHistory(Transport transport, int page, int count) {
        return rentRepository.findByTransport(transport, PageRequest.of(page, count))
                .map(Rent::getTransport)
                .getContent();
    }

    @Transactional
    public HttpStatus rentTransport(Transport transport, Rent.Type rentType) {
        if (!transport.isRented()) return HttpStatus.IM_USED;

        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findById(user.getId()).orElseThrow();
        if (account.getBalance().doubleValue() < transport.getMinutePrice() ||
                account.getId() == transport.getOwner().getId()) return HttpStatus.LOCKED;

        rentRepository.save(new Rent(new Timestamp(System.currentTimeMillis()), rentType, transport, account));
        transport.setRented(false);
        transportRepository.save(transport);
        return HttpStatus.CREATED;
    }

    @Transactional
    public HttpStatus rentClose(Rent rent, double lat, double lon) {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (rent.getRenter().getId() != user.getId() ||
                rent.getTimeClose() != null) return HttpStatus.FORBIDDEN;

        Account account = accountRepository.findById(user.getId()).orElseThrow();
        rent.setTimeClose(new Timestamp(System.currentTimeMillis()));
        rent.getTransport().setRented(true);
        rent.getTransport().setLatitude(lat);
        rent.getTransport().setLongitude(lon);
        double subtractTime = rent.getTimeClose().getTime() - rent.getTimeOpen().getTime();
        int minutes = (int) Math.ceil(subtractTime / 1000 / 60);
        int days = (int) Math.ceil(minutes / 60.0 / 24);
        BigDecimal amount = rent.getRentType() == Rent.Type.Minutes
                ? BigDecimal.valueOf(rent.getTransport().getMinutePrice()).multiply(BigDecimal.valueOf(minutes))
                : BigDecimal.valueOf(rent.getTransport().getDayPrice()).multiply(BigDecimal.valueOf(days));
        paymentRepository.save(new Payment(account, amount, rent));
        accountRepository.save(account);
        transportRepository.save(rent.getTransport());
        rentRepository.save(rent);
        return HttpStatus.OK;
    }
}
