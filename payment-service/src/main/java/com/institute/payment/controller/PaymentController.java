package com.institute.payment.controller;

import com.institute.payment.dto.PaymentRequest;
import com.institute.payment.dto.PaymentResponse;
import com.institute.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public PaymentResponse createPayment(
            @Valid @RequestBody PaymentRequest request) {

        return paymentService.createPayment(request,httpServletRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public PaymentResponse getPaymentById(
            @PathVariable Long id) {

        return paymentService.getPaymentById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<PaymentResponse> getAllPayments() {

        return paymentService.getAllPayments();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PaymentResponse updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequest request) {

        return paymentService.updatePayment(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePayment(
            @PathVariable Long id) {

        return paymentService.deletePayment(id);
    }
}