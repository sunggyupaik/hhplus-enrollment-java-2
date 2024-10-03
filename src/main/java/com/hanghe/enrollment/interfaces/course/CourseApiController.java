package com.hanghe.enrollment.interfaces.course;

import com.hanghe.enrollment.application.course.CourseFacade;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseApiController {
    private final CourseFacade courseFacade;

    @GetMapping
    public List<CourseDto.Response> listCourses(CourseDto.CourseDateRequest request) {
        return courseFacade.listCourses(request);
    }
}
