package com.hanghe.enrollment.domain.enrollment;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getEnrollment(Long studentId);
}
