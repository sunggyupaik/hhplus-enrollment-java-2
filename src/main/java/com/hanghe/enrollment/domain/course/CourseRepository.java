package com.hanghe.enrollment.domain.course;

import java.util.Optional;

public interface CourseRepository {
    Optional<Course> findById(Long courseId);
}
