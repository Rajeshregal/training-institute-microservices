package com.institute.payment.repository;

import com.institute.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentCode(String paymentCode);

    Optional<Payment> findByTransactionId(String transactionId);

    boolean existsByPaymentCode(String paymentCode);
}