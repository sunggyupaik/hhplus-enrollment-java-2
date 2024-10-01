package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDateRequestDto;

import java.util.List;

public interface CourseService {
    List<Course> findAllByDate(CourseDateRequestDto request);
}
