package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.common.exception.CourseNotFoundException;
import com.hanghe.enrollment.common.exception.CourseOptionNotFoundException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.CourseReader;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseReaderImpl implements CourseReader {
    private final CourseRepository courseRepository;
    private final CourseOptionRepository courseOptionRepository;

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    @Override
    public Course getCourse(Long courseId, Long courseOptionId) {
        Course course = getCourse(courseId);

        CourseOption courseOption = course.getCourseOptions().stream()
                .filter(option -> option.getId().equals(courseOptionId))
                .findFirst()
                .orElseThrow(() -> new CourseOptionNotFoundException(courseId));

        course.setCourseOption(courseOption);

        return course;
    }

    @Override
    public List<CourseDto.Response> getCourses(CourseDate courseDate) {
        List<CourseOption> courseOptions = courseOptionRepository.findAllByCourseDate(courseDate);

        return courseOptions.stream()
                .map(courseOption -> {
                    Course detailedCourse = this.getCourse(courseOption.getCourse().getId());
                    return CourseDto.Response.of(detailedCourse, courseOptions);
                })
                .collect(Collectors.toList());
    }
}
