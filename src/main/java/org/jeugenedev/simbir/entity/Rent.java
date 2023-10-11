package org.jeugenedev.simbir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @Column(name = "rent_id")
    private long id;
    private double latitude, longitude, radius;

    public enum Type {
        Car, Bike, Scooter, All
    }
}
