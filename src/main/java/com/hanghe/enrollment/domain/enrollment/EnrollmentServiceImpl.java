package com.hanghe.enrollment.domain.enrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public List<Enrollment> getEnrollment(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
