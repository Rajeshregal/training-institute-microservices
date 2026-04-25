package com.institute.teacher.service.impl;

import com.institute.teacher.dto.TeacherRequest;
import com.institute.teacher.dto.TeacherResponse;
import com.institute.teacher.entity.Teacher;
import com.institute.teacher.repository.TeacherRepository;
import com.institute.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public TeacherResponse createTeacher(TeacherRequest request) {

        if (teacherRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Teacher teacher = Teacher.builder()
                .teacherCode(request.getTeacherCode())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .specialization(request.getSpecialization())
                .salary(request.getSalary())
                .build();

        teacherRepository.save(teacher);

        return mapToResponse(teacher);
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Teacher not found"));

        return mapToResponse(teacher);
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TeacherResponse updateTeacher(
            Long id,
            TeacherRequest request) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Teacher not found"));

        teacher.setFullName(request.getFullName());
        teacher.setPhone(request.getPhone());
        teacher.setSpecialization(request.getSpecialization());
        teacher.setSalary(request.getSalary());

        teacherRepository.save(teacher);

        return mapToResponse(teacher);
    }

    @Override
    public String deleteTeacher(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Teacher not found"));

        teacherRepository.delete(teacher);

        return "Teacher deleted successfully";
    }

    private TeacherResponse mapToResponse(Teacher teacher) {
        return TeacherResponse.builder()
                .id(teacher.getId())
                .teacherCode(teacher.getTeacherCode())
                .fullName(teacher.getFullName())
                .email(teacher.getEmail())
                .phone(teacher.getPhone())
                .specialization(teacher.getSpecialization())
                .salary(teacher.getSalary())
                .joiningDate(teacher.getJoiningDate())
                .status(teacher.getStatus())
                .build();
    }
}