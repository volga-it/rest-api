package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;

@Data
@Entity
@Table(name = "transports")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_id")
    private long id;
    private boolean rented;
    @Column(name = "ttype")
    private long typeId;
    private String model, color, identifier, description;
    private double latitude, longitude, price;

    public enum Type {
        Car(1), Bike(2), Scooter(3);

        private final long id;

        Type(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public static Type byId(long id) {
            return Arrays.stream(Type.values()).filter(type -> type.id == id).findFirst().orElseThrow();
        }
    }
}
