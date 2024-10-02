package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
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

    @Override
    public Enrollment apply(EnrollmentDto.applyRequest request) {
        return enrollmentRepository.save(Enrollment.builder().build());
    }
}
