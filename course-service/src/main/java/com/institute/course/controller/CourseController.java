package com.institute.course.controller;

import com.institute.course.dto.CourseRequest;
import com.institute.course.dto.CourseResponse;
import com.institute.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponse createCourse(
            @Valid @RequestBody CourseRequest request) {

        return courseService.createCourse(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','STUDENT')")
    public CourseResponse getCourseById(
            @PathVariable Long id) {

        return courseService.getCourseById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','STUDENT')")
    public List<CourseResponse> getAllCourses() {

        return courseService.getAllCourses();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponse updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {

        return courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCourse(
            @PathVariable Long id) {

        return courseService.deleteCourse(id);
    }
}