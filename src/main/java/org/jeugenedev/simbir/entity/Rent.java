package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;

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
    @ManyToOne
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;
    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    private Account renter;
}
