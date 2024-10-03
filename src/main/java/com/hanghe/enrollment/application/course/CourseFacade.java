package com.hanghe.enrollment.application.course;

import com.hanghe.enrollment.domain.course.CourseService;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseFacade {
    private final CourseService courseService;

    public List<CourseDto.Response> listCourses(CourseDto.CourseDateRequest request) {
        return courseService.list(request);
    }
}
