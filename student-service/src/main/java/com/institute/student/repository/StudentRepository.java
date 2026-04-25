package com.institute.student.repository;

import com.institute.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    boolean existsByStudentCode(String studentCode);
}