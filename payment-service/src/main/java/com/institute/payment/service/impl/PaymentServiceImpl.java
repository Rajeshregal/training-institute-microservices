package com.institute.payment.service.impl;

import com.institute.payment.dto.PaymentRequest;
import com.institute.payment.dto.PaymentResponse;
import com.institute.payment.entity.Payment;
import com.institute.payment.repository.PaymentRepository;
import com.institute.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        if (paymentRepository.existsByPaymentCode(request.getPaymentCode())) {
            throw new RuntimeException("Payment code already exists");
        }

        Payment payment = Payment.builder()
                .paymentCode(request.getPaymentCode())
                .studentCode(request.getStudentCode())
                .courseCode(request.getCourseCode())
                .amount(request.getAmount())
                .paymentMode(request.getPaymentMode())
                .transactionId(request.getTransactionId())
                .build();

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PaymentResponse updatePayment(
            Long id,
            PaymentRequest request) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        payment.setAmount(request.getAmount());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setTransactionId(request.getTransactionId());

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    @Override
    public String deletePayment(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        paymentRepository.delete(payment);

        return "Payment deleted successfully";
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .paymentCode(payment.getPaymentCode())
                .studentCode(payment.getStudentCode())
                .courseCode(payment.getCourseCode())
                .amount(payment.getAmount())
                .paymentMode(payment.getPaymentMode())
                .transactionId(payment.getTransactionId())
                .paymentDate(payment.getPaymentDate())
                .status(payment.getStatus())
                .build();
    }
}