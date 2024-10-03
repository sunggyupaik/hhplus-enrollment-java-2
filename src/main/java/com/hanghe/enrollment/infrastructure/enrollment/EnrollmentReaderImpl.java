package com.hanghe.enrollment.infrastructure.enrollment;

import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.enrollment.EnrollmentReader;
import com.hanghe.enrollment.domain.enrollment.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnrollmentReaderImpl implements EnrollmentReader {
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public List<Enrollment> getEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Enrollment> lists() {
        return enrollmentRepository.findAll();
    }

    @Override
    public boolean exists(Long studentId, Long courseId, Long courseOptionId) {
        return enrollmentRepository.exists(studentId, courseId, courseOptionId);
    }
}
