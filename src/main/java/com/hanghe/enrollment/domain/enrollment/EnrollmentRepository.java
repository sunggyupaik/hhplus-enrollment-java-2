package com.hanghe.enrollment.domain.enrollment;

import java.util.List;

public interface EnrollmentRepository {
    List<Enrollment> findByStudentId(Long studentId);

    Enrollment save(Enrollment enrollment);

    List<Enrollment> findAll();

    boolean exists(Long studentId, Long courseId, Long courseOptionId);
}
