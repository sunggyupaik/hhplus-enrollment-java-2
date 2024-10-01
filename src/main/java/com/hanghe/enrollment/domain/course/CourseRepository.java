package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDateRequestDto;

import java.util.List;

public interface CourseRepository {
    List<Course> findAllByCourseTime(CourseDateRequestDto courseTime);

    Course save(Course course);
}
