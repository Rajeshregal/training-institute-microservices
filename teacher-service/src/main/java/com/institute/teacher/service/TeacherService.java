package com.institute.teacher.service;

import com.institute.teacher.dto.TeacherRequest;
import com.institute.teacher.dto.TeacherResponse;

import java.util.List;

public interface TeacherService {

    TeacherResponse createTeacher(TeacherRequest request);

    TeacherResponse getTeacherById(Long id);

    List<TeacherResponse> getAllTeachers();

    TeacherResponse updateTeacher(Long id, TeacherRequest request);

    String deleteTeacher(Long id);
}