package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto.Response> list(CourseDto.CourseDateRequest request);
}
