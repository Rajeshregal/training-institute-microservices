package com.institute.teacher.repository;

import com.institute.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmail(String email);

    Optional<Teacher> findByTeacherCode(String teacherCode);

    boolean existsByEmail(String email);

    boolean existsByTeacherCode(String teacherCode);
}