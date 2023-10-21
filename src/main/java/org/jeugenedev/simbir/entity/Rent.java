package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.jeugenedev.simbir.entity.converter.TransportTypeConverter;

@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private long id;
    private double latitude, longitude, radius;
    @Convert(converter = TransportTypeConverter.class)
    @Column(name = "rtype")
    private Transport.Type type;
}
