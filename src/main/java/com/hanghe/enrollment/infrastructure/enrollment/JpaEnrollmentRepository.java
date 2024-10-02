package com.hanghe.enrollment.infrastructure.enrollment;

import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.enrollment.EnrollmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEnrollmentRepository
        extends EnrollmentRepository, JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
}
