package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDto;
import com.hanghe.enrollment.domain.course.option.CourseDate;

import java.util.List;

public interface CourseReader {
    Course getCourse(Long courseId);

    Course getCourse(Long courseId, Long courseOptionId);
    List<CourseDto.Response> getCourses(CourseDate courseDate);
}
