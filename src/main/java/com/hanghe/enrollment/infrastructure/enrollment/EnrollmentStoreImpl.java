package com.hanghe.enrollment.infrastructure.enrollment;

import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.enrollment.EnrollmentRepository;
import com.hanghe.enrollment.domain.enrollment.EnrollmentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnrollmentStoreImpl implements EnrollmentStore {
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment store(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
