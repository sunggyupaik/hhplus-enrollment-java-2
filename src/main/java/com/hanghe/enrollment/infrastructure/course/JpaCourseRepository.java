package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.course.dto.CourseDateRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCourseRepository
        extends CourseRepository, JpaRepository<Course, Long> {
    List<Course> findAllByCourseTime(CourseDateRequestDto courseTime);

    Course save(Course course);
}
