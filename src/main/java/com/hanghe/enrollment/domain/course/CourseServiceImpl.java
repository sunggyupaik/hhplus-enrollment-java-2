package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseReader courseReader;

    @Override
    @Transactional(readOnly = true)
    public List<CourseDto.Response> list(CourseDto.CourseDateRequest request) {
        return courseReader.getCourses(request.toCourseDate());
    }
}
