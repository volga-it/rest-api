package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;

@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private long id;
    private double latitude, longitude, radius;
    @Column(name = "rtype")
    private long typeId;

    public enum Type {
        Car(1), Bike(2), Scooter(3), All(4);

        private final long id;

        Type(long id) {
            this.id = id;
        }

        public static Type byId(long id) {
            return Arrays.stream(Type.values()).filter(type -> type.id == id).findFirst().orElseThrow();
        }

        public long getId() {
            return id;
        }
    }
}
