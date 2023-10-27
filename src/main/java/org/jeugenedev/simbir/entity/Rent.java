package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "rents")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private long id;
    private Timestamp timeOpen, timeClose;
    @RestResource(exported = false)
    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;
    @RestResource(exported = false)
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Account renter;
}
