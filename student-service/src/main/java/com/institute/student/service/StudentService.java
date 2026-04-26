package com.institute.student.service;

import com.institute.student.dto.StudentRequest;
import com.institute.student.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();

    StudentResponse updateStudent(Long id, StudentRequest request);

    String deleteStudent(Long id);

    StudentResponse getStudentByCode(String studentCode);
}