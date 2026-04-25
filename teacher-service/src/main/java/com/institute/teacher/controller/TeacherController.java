package com.institute.teacher.controller;

import com.institute.teacher.dto.TeacherRequest;
import com.institute.teacher.dto.TeacherResponse;
import com.institute.teacher.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TeacherResponse createTeacher(
            @Valid @RequestBody TeacherRequest request) {

        return teacherService.createTeacher(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    public TeacherResponse getTeacherById(
            @PathVariable Long id) {

        return teacherService.getTeacherById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    public List<TeacherResponse> getAllTeachers() {

        return teacherService.getAllTeachers();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TeacherResponse updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherRequest request) {

        return teacherService.updateTeacher(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTeacher(
            @PathVariable Long id) {

        return teacherService.deleteTeacher(id);
    }
}