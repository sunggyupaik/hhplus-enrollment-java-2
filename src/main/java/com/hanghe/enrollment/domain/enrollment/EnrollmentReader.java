package com.hanghe.enrollment.domain.enrollment;

import java.util.List;

public interface EnrollmentReader {
    List<Enrollment> getEnrollments(Long studentId);

    List<Enrollment> lists();

    boolean exists(Long studentId, Long courseId, Long courseOptionId);
}
