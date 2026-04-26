package com.institute.payment.dto;

import com.institute.payment.enums.StudentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponse {

    private Long id;
    private String studentCode;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private LocalDate joiningDate;
    private StudentStatus status;
}