package com.institute.payment.dto;

import com.institute.payment.enums.PaymentMode;
import com.institute.payment.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PaymentResponse {

    private Long id;
    private String paymentCode;
    private String studentCode;
    private String courseCode;
    private BigDecimal amount;
    private PaymentMode paymentMode;
    private String transactionId;
    private LocalDate paymentDate;
    private PaymentStatus status;
}