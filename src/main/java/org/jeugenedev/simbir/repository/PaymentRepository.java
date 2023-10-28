package org.jeugenedev.simbir.repository;

import org.jeugenedev.simbir.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

@RestResource(exported = false)
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
