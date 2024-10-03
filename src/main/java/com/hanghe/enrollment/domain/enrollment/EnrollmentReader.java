package com.hanghe.enrollment.domain.enrollment;

import java.util.List;

public interface EnrollmentReader {
    List<Enrollment> getEnrollments(Long studentId);
}
