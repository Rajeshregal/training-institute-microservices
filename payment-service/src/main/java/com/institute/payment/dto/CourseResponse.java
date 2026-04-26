package com.institute.payment.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseResponse {

    private Long id;
    private String courseCode;
    private String courseName;
    private String duration;
    private BigDecimal fee;
    private String teacherCode;
}