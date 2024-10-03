package com.hanghe.enrollment.interfaces.course;

import com.hanghe.enrollment.application.course.CourseFacade;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseFixture;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseApiController.class)
class CourseApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseFacade courseFacade;

    private static final Integer YEAR_2024 = 2024;
    private static final Integer MONTH_10 = 10;
    private static final Integer DAY_31 = 31;

    private static final Long PROFESSOR_1_ID = 10L;
    private static final String PROFESSOR_1_NAME = "강연자1";
    private static final String PROFESSOR_1_EMAIL = "1@naver.com";
    private static final String PROFESSOR_1_PHONE = "01011111111";

    private static final Long PROFESSOR_2_ID = 11L;
    private static final String PROFESSOR_2_NAME = "강연자2";
    private static final String PROFESSOR_2_EMAIL = "2@naver.com";
    private static final String PROFESSOR_2_PHONE = "01022222222";

    private static final Long COURSE_1_ID = 1L;
    private static final Long COURSE_OPTION_1_ID = 50L;
    private static final String COURSE_1_TITLE = "코스1 특강";

    private static final Long COURSE_2_ID = 2L;
    private static final Long COURSE_OPTION_2_ID = 51L;
    private static final String COURSE_2_TITLE = "코스2 특강";

    CourseDto.CourseDateRequest courseDateRequest;

    private Professor professor_1;
    private UserInfo professorUserInfo_1;
    private Professor professor_2;
    private UserInfo professorUserInfo_2;

    private CourseOption courseOption_1;
    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

    private CourseOption courseOption_2;
    private CourseTime courseTime_2;
    private CourseDate courseDate_2;
    private Course course_2;

    private List<CourseDto.Response> responses;


    @BeforeEach
    void setUp() {
        courseDateRequest = CourseDto.CourseDateRequest.builder()
                .year(YEAR_2024)
                .month(MONTH_10)
                .day(DAY_31)
                .build();

        professorUserInfo_1 = UserInfoFixture.createUserInfo(PROFESSOR_1_NAME, PROFESSOR_1_EMAIL, PROFESSOR_1_PHONE);
        professor_1 = ProfessorFixture.createProfessor(PROFESSOR_1_ID, professorUserInfo_1);

        professorUserInfo_2 = UserInfoFixture.createUserInfo(PROFESSOR_2_NAME, PROFESSOR_2_EMAIL, PROFESSOR_2_PHONE);
        professor_2 = ProfessorFixture.createProfessor(PROFESSOR_2_ID, professorUserInfo_2);

        courseDate_1 = CourseOptionFixture.createCourseDate(YEAR_2024, MONTH_10, DAY_31);
        courseTime_1 = CourseOptionFixture.createCourseTime(12, 0, 14, 0);
        courseOption_1 = CourseOptionFixture.createCourseOption(COURSE_OPTION_1_ID, courseDate_1, courseTime_1);
        course_1 = CourseFixture.createCourse(COURSE_1_ID, COURSE_1_TITLE, professor_1);
        course_1.addCourseOption(courseOption_1);

        courseDate_2 = CourseOptionFixture.createCourseDate(YEAR_2024, MONTH_10, DAY_31);
        courseTime_2 = CourseOptionFixture.createCourseTime(14, 0, 16, 0);
        courseOption_2 = CourseOptionFixture.createCourseOption(COURSE_OPTION_2_ID, courseDate_2, courseTime_2);
        course_2 = CourseFixture.createCourse(COURSE_2_ID, COURSE_2_TITLE, professor_2);
        course_2.addCourseOption(courseOption_2);

        responses = List.of(
                CourseDto.Response.of(course_1, List.of(courseOption_1)),
                CourseDto.Response.of(course_2, List.of(courseOption_2))
        );
    }

    @Nested
    @DisplayName("listCourses 메서드는")
    class Describe_listCourses {
        @Nested
        @DisplayName("특강이 존재하는 날짜가 주어진다면")
        class Context_WithDateThatExistsCourses {
            private final CourseDto.CourseDateRequest request = CourseDto.CourseDateRequest.builder()
                    .year(YEAR_2024)
                    .month(MONTH_10)
                    .day(DAY_31)
                    .build();

            @Test
            @DisplayName("해당 특강 목록을 반환한다")
            void itReturnsCourses() throws Exception {
                given(courseFacade.listCourses(any(CourseDto.CourseDateRequest.class))).willReturn(responses);

                mockMvc.perform(
                                get("/api/course")
                                        .param("year", String.valueOf(request.getYear()))
                                        .param("month", String.valueOf(request.getMonth()))
                                        .param("day", String.valueOf(request.getDay()))
                        )
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$.[0].id").value(COURSE_1_ID))
                        .andExpect(jsonPath("$.[1].id").value(COURSE_2_ID));
            }
        }

        @Nested
        @DisplayName("특강이 존재하지 않는 날짜가 주어진다면")
        class Context_WithDateThatNotExistsCourses {
            private final CourseDto.CourseDateRequest request = CourseDto.CourseDateRequest.builder()
                    .year(9999)
                    .month(12)
                    .day(31)
                    .build();

            @Test
            @DisplayName("빈 목록을 반환한다")
            void itReturnsEmptyList() throws Exception {
                given(courseFacade.listCourses(any(CourseDto.CourseDateRequest.class))).willReturn(List.of());

                mockMvc.perform(
                                get("/api/course", request)
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }
    }
}