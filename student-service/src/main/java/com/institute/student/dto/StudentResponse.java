package com.institute.student.dto;

import com.institute.student.enums.StudentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
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