package org.jeugenedev.simbir.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @Column(name = "payment_id")
    private UUID id = UUID.randomUUID();
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "payer")
    private Account payer;
    private BigDecimal amount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent")
    private Rent rent;

    public Payment(Account payer, BigDecimal amount, Rent rent) {
        this.payer = payer;
        this.amount = amount;
        this.rent = rent;
    }
}
