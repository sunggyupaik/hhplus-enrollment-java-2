package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDateRequestDto;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.infrastructure.course.JpaCourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CourseServiceImplTest {
    private CourseService courseService;
    private CourseRepository courseRepository;

    private static final Long PROFESSOR_1_ID = 10L;
    private static final String PROFESSOR_1_NAME = "강연자1";
    private static final String PROFESSOR_1_EMAIL = "1@naver.com";
    private static final String PROFESSOR_1_PHONE = "01011111111";

    private static final Long PROFESSOR_2_ID = 11L;
    private static final String PROFESSOR_2_NAME = "강연자2";
    private static final String PROFESSOR_2_EMAIL = "2@naver.com";
    private static final String PROFESSOR_2_PHONE = "01022222222";

    private static final Long COURSE_1_ID = 1L;
    private static final Long COURSE_2_ID = 2L;
    private static final String COURSE_1_TITLE = "코스1 특강";
    private static final String COURSE_2_TITLE = "코스2 특강";

    private Professor professor_1;
    private UserInfo professorUserInfo_1;
    private Professor professor_2;
    private UserInfo professorUserInfo_2;

    private CourseDateRequestDto courseDateRequestDto;
    private CourseTime courseTime_1;
    private CourseTime courseTime_2;
    private Course course_1;
    private Course course_2;

    @BeforeEach
    void setUp() {
        courseRepository = mock(JpaCourseRepository.class);
        courseService = new CourseServiceImpl(courseRepository);


        professorUserInfo_1 = UserInfo.builder()
                .name(PROFESSOR_1_NAME)
                .email(PROFESSOR_1_EMAIL)
                .phone(PROFESSOR_1_PHONE)
                .build();

        professor_1 = Professor.builder()
                .id(PROFESSOR_1_ID)
                .userInfo(professorUserInfo_1)
                .build();

        professorUserInfo_2 = UserInfo.builder()
                .name(PROFESSOR_2_NAME)
                .email(PROFESSOR_2_EMAIL)
                .phone(PROFESSOR_2_PHONE)
                .build();

        professor_2 = Professor.builder()
                .id(PROFESSOR_2_ID)
                .userInfo(professorUserInfo_2)
                .build();

        courseDateRequestDto = CourseDateRequestDto.builder()
                .year(2024)
                .month(10)
                .day(31)
                .build();

        courseTime_1 = CourseTime.builder()
                .year(2024)
                .month(10)
                .day(31)
                .startTime(12)
                .startMinute(0)
                .endTime(14)
                .endMinute(0)
                .build();

        courseTime_2 = CourseTime.builder()
                .year(2024)
                .month(10)
                .day(31)
                .startTime(14)
                .startMinute(0)
                .endTime(16)
                .endMinute(0)
                .build();

        course_1 = Course.builder()
                .id(COURSE_1_ID)
                .title(COURSE_1_TITLE)
                .courseTime(courseTime_1)
                .professor(professor_1)
                .build();

        course_2 = Course.builder()
                .id(COURSE_2_ID)
                .title(COURSE_2_TITLE)
                .courseTime(courseTime_2)
                .professor(professor_2)
                .build();
    }

    @Test
    @DisplayName("주어진 특정 날짜로 특강 목록을 조회하여 반환한다")
    void listCoursesWithCourseTime() {
        given(courseRepository.findAllByCourseTime(courseDateRequestDto)).willReturn(List.of(course_1, course_2));

        List<Course> courses = courseService.findAllByDate(courseDateRequestDto);

        assertThat(courses).hasSize(2);
    }
}