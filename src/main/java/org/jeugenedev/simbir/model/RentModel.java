package org.jeugenedev.simbir.model;

import org.jeugenedev.simbir.configuration.SecurityConfiguration;
import org.jeugenedev.simbir.entity.Account;
import org.jeugenedev.simbir.entity.Rent;
import org.jeugenedev.simbir.entity.Transport;
import org.jeugenedev.simbir.repository.RentRepository;
import org.jeugenedev.simbir.repository.TransportRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentModel {
    private final RentRepository rentRepository;
    private final TransportRepository transportRepository;

    public RentModel(RentRepository rentRepository, TransportRepository transportRepository) {
        this.rentRepository = rentRepository;
        this.transportRepository = transportRepository;
    }

    public List<Transport> transport(double lat, double lon, double radius, Transport.Type type, int page, int count) {
        return transportRepository.findTransportByRadius(lat, lon, radius, type, PageRequest.of(page, count)).getContent();
    }

    public List<Rent> myHistory(int page, int count) {
        SecurityConfiguration.User user = (SecurityConfiguration.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return rentRepository.findByRenter(new Account(user.getId()), PageRequest.of(page, count)).getContent();
    }
}
