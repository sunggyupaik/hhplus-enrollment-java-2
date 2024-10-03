package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.course.dto.CourseDto;
import com.hanghe.enrollment.domain.course.dto.CourseDto.CourseDateRequest;
import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseTime;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.UserInfoFixture;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.professor.ProfessorFixture;
import com.hanghe.enrollment.interfaces.course.option.CourseOptionFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CourseServiceImplTest {
    private CourseService courseService;
    private CourseReader courseReader;

    private static final Long PROFESSOR_1_ID = 10L;
    private static final String PROFESSOR_1_NAME = "강연자1";
    private static final String PROFESSOR_1_EMAIL = "1@naver.com";
    private static final String PROFESSOR_1_PHONE = "01011111111";

    private static final Long PROFESSOR_2_ID = 11L;
    private static final String PROFESSOR_2_NAME = "강연자2";
    private static final String PROFESSOR_2_EMAIL = "2@naver.com";
    private static final String PROFESSOR_2_PHONE = "01022222222";

    private static final Integer YEAR_2024 = 2024;
    private static final Integer MONTH_10 = 10;
    private static final Integer DAY_31 = 31;

    private static final Long COURSE_1_ID = 1L;
    private static final Long COURSE_OPTION_1_1_ID = 50L;
    private static final Long COURSE_OPTION_1_2_ID = 52L;
    private static final String COURSE_1_TITLE = "코스1 특강";

    private static final Long COURSE_2_ID = 2L;
    private static final Long COURSE_OPTION_2_ID = 51L;
    private static final String COURSE_2_TITLE = "코스2 특강";

    private Professor professor_1;
    private UserInfo professorUserInfo_1;
    private Professor professor_2;
    private UserInfo professorUserInfo_2;

    private CourseOption courseOption_1_1;
    private CourseOption courseOption_1_2;
    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

    private CourseOption courseOption_2;
    private CourseTime courseTime_2;
    private CourseDate courseDate_2;
    private Course course_2;

    private CourseDto.CourseDateRequest courseDateRequest;
    private CourseDateRequest courseDateRequest_none;
    private CourseDate courseDate_none;

    @BeforeEach
    void setUp() {
        courseReader = mock(CourseReader.class);
        courseService = new CourseServiceImpl(courseReader);

        professorUserInfo_1 = UserInfoFixture.createUserInfo(PROFESSOR_1_NAME, PROFESSOR_1_EMAIL, PROFESSOR_1_PHONE);
        professor_1 = ProfessorFixture.createProfessor(PROFESSOR_1_ID, professorUserInfo_1);

        professorUserInfo_2 = UserInfoFixture.createUserInfo(PROFESSOR_2_NAME, PROFESSOR_2_EMAIL, PROFESSOR_2_PHONE);
        professor_2 = ProfessorFixture.createProfessor(PROFESSOR_2_ID, professorUserInfo_2);

        courseDate_1 = CourseOptionFixture.createCourseDate(YEAR_2024, MONTH_10, DAY_31);
        courseTime_1 = CourseOptionFixture.createCourseTime(12, 0, 14, 0);
        courseOption_1_1 = CourseOptionFixture.createCourseOption(COURSE_OPTION_1_1_ID, courseDate_1, courseTime_1);
        courseOption_1_2 = CourseOptionFixture.createCourseOption(COURSE_OPTION_1_2_ID, courseDate_1, courseTime_2);
        course_1 = CourseFixture.createCourse(COURSE_1_ID, COURSE_1_TITLE, professor_1);
        course_1.addCourseOption(courseOption_1_1);
        course_1.addCourseOption(courseOption_1_2);

        courseDate_2 = CourseOptionFixture.createCourseDate(YEAR_2024, MONTH_10, DAY_31);
        courseTime_2 = CourseOptionFixture.createCourseTime(14, 0, 16, 0);
        courseOption_2 = CourseOptionFixture.createCourseOption(COURSE_OPTION_2_ID, courseDate_2, courseTime_2);
        course_2 = CourseFixture.createCourse(COURSE_2_ID, COURSE_2_TITLE, professor_2);
        course_2.addCourseOption(courseOption_2);

        courseDateRequest = CourseDto.CourseDateRequest.builder()
                .year(YEAR_2024)
                .month(MONTH_10)
                .day(DAY_31)
                .build();

        courseDate_none = CourseDate.builder()
                .year(9999)
                .month(12)
                .day(31)
                .build();

        courseDateRequest_none = CourseDateRequest.builder()
                .year(9999)
                .month(12)
                .day(31)
                .build();
    }

    @Test
    @DisplayName("주어진 특정 날짜로 특강 목록을 조회하여 반환한다")
    void listCoursesWithCourseDate() {
        given(courseReader.getCourses(courseDate_1))
                .willReturn(
                        List.of(CourseDto.Response.of(course_1, List.of(courseOption_1_1, courseOption_1_2)),
                                CourseDto.Response.of(course_2, List.of(courseOption_2)))
                );

        List<CourseDto.Response> courses = courseService.list(courseDateRequest);

        assertThat(courses).hasSize(2);
        assertThat(courses.get(0).getId()).isEqualTo(COURSE_1_ID);
        assertThat(courses.get(0).getProfessor().getId()).isEqualTo(PROFESSOR_1_ID);
        assertThat(courses.get(0).getCourseOptions().get(0).getId()).isEqualTo(COURSE_OPTION_1_1_ID);
        assertThat(courses.get(0).getCourseOptions().get(1).getId()).isEqualTo(COURSE_OPTION_1_2_ID);
    }

    @Test
    @DisplayName("주어진 특정 날짜에 특강이 없으면 빈 리스트를 반환한다.")
    void listEmptyCoursesWithNotExistedDate() {
        given(courseReader.getCourses(courseDate_none)).willReturn(List.of());

        List<CourseDto.Response> courses = courseService.list(courseDateRequest_none);

        assertThat(courses).hasSize(0);
    }
}
