package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.common.exception.CourseNotFoundException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseReader;
import com.hanghe.enrollment.domain.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseReaderImpl implements CourseReader {
    private final CourseRepository courseRepository;

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }
}
