package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeugenedev.simbir.entity.converter.RentTypeConverter;
import org.springframework.data.rest.core.annotation.RestResource;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @Column(name = "rent_id")
    private UUID id = UUID.randomUUID();
    private Timestamp timeOpen, timeClose;
    @Convert(converter = RentTypeConverter.class)
    private Type rentType;
    @RestResource(exported = false)
    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;
    @RestResource(exported = false)
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Account renter;

    public Rent(Timestamp timeOpen, Type rentType, Transport transport, Account renter) {
        this.timeOpen = timeOpen;
        this.rentType = rentType;
        this.transport = transport;
        this.renter = renter;
    }

    public enum Type {
        Minutes(1), Days(2);

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

        public static List<String> toList() {
            return Arrays.stream(Type.values()).map(Enum::name).toList();
        }
    }
}
