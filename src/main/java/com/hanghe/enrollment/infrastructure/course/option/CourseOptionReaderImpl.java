package com.hanghe.enrollment.infrastructure.course.option;

import com.hanghe.enrollment.common.exception.CourseOptionNotFoundException;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseOptionReader;
import com.hanghe.enrollment.domain.course.option.CourseOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseOptionReaderImpl implements CourseOptionReader {
    private final CourseOptionRepository courseOptionRepository;

    @Override
    public CourseOption getByIdForPessimistLock(Long courseId, Long courseOptionId) {
        return courseOptionRepository.findByIdForPessimistLock(courseId, courseOptionId)
            .orElseThrow(() -> new CourseOptionNotFoundException(courseOptionId));
    }

    @Override
    public CourseOption getCourseOption(Long courseOptionId) {
        return courseOptionRepository.findById(courseOptionId)
                .orElseThrow(() -> new CourseOptionNotFoundException(courseOptionId));
    }
}
