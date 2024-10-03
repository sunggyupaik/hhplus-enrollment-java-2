package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.common.exception.CourseNotFoundException;
import com.hanghe.enrollment.common.exception.CourseOptionNotFoundException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseFixture;
import com.hanghe.enrollment.domain.course.CourseReader;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOptionRepository;
import com.hanghe.enrollment.domain.course.option.CourseTime;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.UserInfoFixture;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.professor.ProfessorFixture;
import com.hanghe.enrollment.interfaces.course.option.CourseOptionFixture;
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

    private static final Long PROFESSOR_1_ID = 10L;
    private static final String PROFESSOR_1_NAME = "강연자1";
    private static final String PROFESSOR_1_EMAIL = "강연자1@naver.com";
    private static final String PROFESSOR_1_PHONE = "01011111111";

    private static final Long NOT_EXISTED_COURSE_ID = 999L;
    private static final Long NOT_EXISTED_COURSE_OPTION_ID = 998L;
    private static final Long COURSE_1_ID = 1L;
    private static final String COURSE_1_TITLE = "코스1 특강";

    private Professor professor_1;
    private UserInfo professorUserInfo_1;

    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

    @BeforeEach
    void setUp() {
        professorUserInfo_1 = UserInfoFixture.createUserInfo(PROFESSOR_1_NAME, PROFESSOR_1_EMAIL, PROFESSOR_1_PHONE);
        professor_1 = ProfessorFixture.createProfessor(PROFESSOR_1_ID, professorUserInfo_1);

        courseRepository = mock(CourseRepository.class);
        courseOptionRepository = mock(CourseOptionRepository.class);
        courseReader = new CourseReaderImpl(courseRepository, courseOptionRepository);

        courseDate_1 = CourseOptionFixture.createCourseDate(2024, 10, 31);
        courseTime_1 = CourseOptionFixture.createCourseTime(12, 0, 14, 0);
        course_1 = CourseFixture.createCourse(COURSE_1_ID, COURSE_1_TITLE, professor_1);
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

    @Test
    @DisplayName("주어진 식별자에 해당하는 특강 옵션이 없으면 찾을 수 없다는 예외를 반환한다.")
    void getCourseWithNotExistedCourseOptionId_throwsNotFoundException() {
        given(courseRepository.findById(COURSE_1_ID)).willReturn(Optional.of(course_1));

        assertThatThrownBy(
                () -> courseReader.getCourse(COURSE_1_ID, NOT_EXISTED_COURSE_OPTION_ID)
        )
                .isInstanceOf(CourseOptionNotFoundException.class);
    }
}