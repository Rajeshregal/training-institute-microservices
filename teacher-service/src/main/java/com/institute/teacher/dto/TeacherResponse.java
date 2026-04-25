package com.institute.teacher.dto;

import com.institute.teacher.enums.TeacherStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TeacherResponse {

    private Long id;
    private String teacherCode;
    private String fullName;
    private String email;
    private String phone;
    private String specialization;
    private BigDecimal salary;
    private LocalDate joiningDate;
    private TeacherStatus status;
}