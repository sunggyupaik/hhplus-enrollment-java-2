package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseReader courseReader;

    @Override
    public List<CourseDto.Response> list(CourseDto.CourseDateRequest request) {
        List<Course> courses = courseReader.getCourses(request.toCourseDate());

        return courses.stream().map(CourseDto.Response::of)
                .collect(Collectors.toList());
    }
}
