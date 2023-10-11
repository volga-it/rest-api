package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transports")
public class Transport {
    @Id
    @Column(name = "transport_id")
    private long id;
    private boolean rented;
    @Column(name = "ttype")
    @Enumerated(EnumType.STRING)
    private Type type;
    private String model, color, identifier, description;
    private double latitude, longitude, price;

    public enum Type {
        Car, Bike, Scooter
    }
}
