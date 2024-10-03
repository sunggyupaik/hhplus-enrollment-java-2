package com.hanghe.enrollment.domain.course;

import java.util.Optional;

public interface CourseRepository {
    Optional<Course> findById(Long courseId);

    Optional<Course> getByIdForPessimistLock(Long courseId, Long courseOptionId);

    Course save(Course course);

    Optional<Course> getByIdForPessimistLock(Long courseId);
}
