package com.hanghe.enrollment.domain.enrollment;

import java.util.List;

public interface EnrollmentRepository {
    List<Enrollment> findByStudentId(Long studentId);

    Enrollment save(Enrollment enrollment);
}
