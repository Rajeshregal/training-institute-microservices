package com.institute.payment.service;

import com.institute.payment.dto.PaymentRequest;
import com.institute.payment.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getAllPayments();

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    String deletePayment(Long id);
}