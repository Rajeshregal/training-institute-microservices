package com.institute.payment.service;

import com.institute.payment.dto.PaymentRequest;
import com.institute.payment.dto.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest paymentRequest, HttpServletRequest request);

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getAllPayments();

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    String deletePayment(Long id);
}