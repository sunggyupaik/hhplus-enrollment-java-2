package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCourseRepository
        extends CourseRepository, JpaRepository<Course, Long> {
    Optional<Course> findById(Long courseId);
}
