package com.hanghe.enrollment.interfaces.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghe.enrollment.application.course.CourseFacade;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseDate;
import com.hanghe.enrollment.domain.course.CourseTime;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.professor.Professor;
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

    @Autowired
    private ObjectMapper objectMapper;

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
    private static final String COURSE_1_TITLE = "코스1 특강";

    private static final Long COURSE_2_ID = 2L;
    private static final String COURSE_2_TITLE = "코스2 특강";

    CourseDto.CourseDateRequest courseDateRequest;

    private Professor professor_1;
    private UserInfo professorUserInfo_1;
    private Professor professor_2;
    private UserInfo professorUserInfo_2;

    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

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

        courseDate_1 = CourseDate.builder()
                .year(YEAR_2024)
                .month(MONTH_10)
                .day(DAY_31)
                .build();

        courseTime_1 = CourseTime.builder()
                .startTime(12)
                .startMinute(0)
                .endTime(14)
                .endMinute(0)
                .build();

        course_1 = Course.builder()
                .id(COURSE_1_ID)
                .title(COURSE_1_TITLE)
                .courseDate(courseDate_1)
                .courseTime(courseTime_1)
                .professor(professor_1)
                .build();

        courseDate_2 = CourseDate.builder()
                .year(YEAR_2024)
                .month(MONTH_10)
                .day(DAY_31)
                .build();

        courseTime_2 = CourseTime.builder()
                .startTime(14)
                .startMinute(0)
                .endTime(16)
                .endMinute(0)
                .build();

        course_2 = Course.builder()
                .id(COURSE_2_ID)
                .title(COURSE_2_TITLE)
                .courseDate(courseDate_2)
                .courseTime(courseTime_2)
                .professor(professor_2)
                .build();

        responses = List.of(CourseDto.Response.of(course_1), CourseDto.Response.of(course_2));
        System.out.println(responses);
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
            @DisplayName("포인트가 0인 빈 유저 포인트를 반환한다")
            void itReturnsEmptyList() throws Exception {
                given(courseFacade.listCourses(request)).willReturn(List.of());

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