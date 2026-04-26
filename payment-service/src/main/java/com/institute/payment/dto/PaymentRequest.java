package com.institute.payment.dto;

import com.institute.payment.enums.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @NotBlank(message = "Payment code is required")
    private String paymentCode;

    @NotBlank(message = "Student code is required")
    private String studentCode;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;

    private String transactionId;
}