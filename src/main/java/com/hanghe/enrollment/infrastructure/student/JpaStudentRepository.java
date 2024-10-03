package com.hanghe.enrollment.infrastructure.student;

import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaStudentRepository
        extends StudentRepository, JpaRepository<Student, Long> {
    Optional<Student> findById(Long id);
}
