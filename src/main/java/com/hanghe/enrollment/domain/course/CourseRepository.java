package com.hanghe.enrollment.domain.course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAllByCourseDate(CourseDate courseDate);

    Optional<Course> findById(Long courseId);
}
