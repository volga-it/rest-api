package org.jeugenedev.simbir.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.jeugenedev.simbir.entity.converter.TransportTypeConverter;

import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "transports")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_id")
    private long id;
    private boolean rented;
    @Convert(converter = TransportTypeConverter.class)
    @Column(name = "ttype")
    private Type type;
    private String model, color, identifier, description;
    private double latitude, longitude;
    @Column(name = "price_minute")
    private double minutePrice;
    @Column(name = "price_day")
    private double dayPrice;
    @JsonIgnore
    @OneToMany(mappedBy = "transport")
    private List<Rent> rents;

    public enum Type {
        Car(1), Bike(2), Scooter(3), All(4);

        private final int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Type byId(int id) {
            return Arrays.stream(Type.values()).filter(type -> type.id == id).findFirst().orElseThrow();
        }
    }
}
