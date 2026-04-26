package com.institute.course.dto;

import com.institute.course.enums.CourseStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CourseResponse {

    private Long id;
    private String courseCode;
    private String courseName;
    private String duration;
    private BigDecimal fee;
    private String teacherCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private CourseStatus status;
}