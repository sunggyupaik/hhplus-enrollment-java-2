package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.common.exception.CourseNotFoundException;
import com.hanghe.enrollment.domain.course.CourseReader;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.course.option.CourseOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CourseReaderImplTest {
    private CourseReader courseReader;
    private CourseOptionRepository courseOptionRepository;
    private CourseRepository courseRepository;

    private static final Long NOT_EXISTED_COURSE_ID = 999L;

    @BeforeEach
    void setUp() {
        courseRepository = mock(CourseRepository.class);
        courseOptionRepository = mock(CourseOptionRepository.class);
        courseReader = new CourseReaderImpl(courseRepository, courseOptionRepository);
    }

    @Test
    @DisplayName("주어진 식별자에 해당하는 특강이 없으면 찾을 수 없다는 예외를 반환한다.")
    void getCourseWithNotExistedId_throwsNotFoundException() {
        given(courseRepository.findById(NOT_EXISTED_COURSE_ID)).willReturn(Optional.empty());

        assertThatThrownBy(
                () -> courseReader.getCourse(NOT_EXISTED_COURSE_ID)
        )
                .isInstanceOf(CourseNotFoundException.class);
    }
}