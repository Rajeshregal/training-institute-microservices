package com.institute.payment.service.impl;

import com.institute.payment.client.StudentClient;
import com.institute.payment.dto.CourseResponse;
import com.institute.payment.dto.PaymentRequest;
import com.institute.payment.dto.PaymentResponse;
import com.institute.payment.dto.StudentResponse;
import com.institute.payment.entity.Payment;
import com.institute.payment.repository.PaymentRepository;
import com.institute.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    // Approach 01 to communicate one Microservice to Other MicroService by using RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest, HttpServletRequest request ) {
        validateStudent(paymentRequest.getStudentCode(),request);
        validateCourse(paymentRequest.getCourseCode(),request);
        if (paymentRepository.existsByPaymentCode(paymentRequest.getPaymentCode())) {
            throw new RuntimeException("Payment code already exists");
        }


        Payment payment = Payment.builder()
                .paymentCode(paymentRequest.getPaymentCode())
                .studentCode(paymentRequest.getStudentCode())
                .courseCode(paymentRequest.getCourseCode())
                .amount(paymentRequest.getAmount())
                .paymentMode(paymentRequest.getPaymentMode())
                .transactionId(paymentRequest.getTransactionId())
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

    private void validateStudent(String studentCode,HttpServletRequest  request){
        String url ="http://localhost:8082/api/student/code/"+studentCode;
        try{
            String authHeader = request.getHeader("Authorization");
            HttpHeaders headers= new HttpHeaders();
            headers.set("Authorization",authHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<StudentResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            StudentResponse.class
                    );

            StudentResponse student = response.getBody();

            if(student == null){
                throw new RuntimeException("Student not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid Student request with :: " +studentCode
            );
        }
    }

    private void validateCourse(String courseCode,HttpServletRequest  request) {

        String url =
                "http://localhost:8084/api/course/code/" + courseCode;

        try {
            String authHeader = request.getHeader("Authorization");
            HttpHeaders headers= new HttpHeaders();
            headers.set("Authorization",authHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<CourseResponse> course =
                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            CourseResponse.class
                    );

            if (course == null) {
                throw new RuntimeException(
                        "Course not found"
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid Course: " + courseCode
            );
        }
    }
}