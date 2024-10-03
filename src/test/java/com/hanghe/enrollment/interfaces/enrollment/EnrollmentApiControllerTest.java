package com.hanghe.enrollment.interfaces.enrollment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghe.enrollment.application.enrollment.EnrollmentFacade;
import com.hanghe.enrollment.common.exception.CourseNotFoundException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseDate;
import com.hanghe.enrollment.domain.course.CourseTime;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.student.Student;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrollmentApiController.class)
class EnrollmentApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentFacade enrollmentFacade;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Long NOT_EXISTED_STUDENT_ID = 999L;
    private static final Long STUDENT_1_ID = 20L;
    private static final String STUDENT_1_NAME = "신청자1";
    private static final String STUDENT_1_EMAIL = "신청자1@naver.com";
    private static final String STUDENT_1_PHONE = "01020202020";

    private static final Long PROFESSOR_1_ID = 10L;
    private static final String PROFESSOR_1_NAME = "강연자1";
    private static final String PROFESSOR_1_EMAIL = "강연자1@naver.com";
    private static final String PROFESSOR_1_PHONE = "01011111111";

    private static final Long PROFESSOR_2_ID = 11L;
    private static final String PROFESSOR_2_NAME = "강연자2";
    private static final String PROFESSOR_2_EMAIL = "2@naver.com";
    private static final String PROFESSOR_2_PHONE = "01022222222";

    private static final Integer YEAR_2024 = 2024;
    private static final Integer MONTH_10 = 10;
    private static final Integer DAY_31 = 31;

    private static final Long NOT_EXISTED_COURSE_ID = 998L;
    private static final Long COURSE_1_ID = 1L;
    private static final String COURSE_1_TITLE = "코스1 특강";

    private static final Long COURSE_2_ID = 2L;
    private static final String COURSE_2_TITLE = "코스2 특강";

    private static final Long ENROLLMENT_1_ID = 30L;
    private static final Long ENROLLMENT_2_ID = 31L;

    private Student student_1;
    private UserInfo studentUserInfo_1;

    private Professor professor_1;
    private UserInfo professorUserInfo_1;
    private Professor professor_2;
    private UserInfo professorUserInfo_2;

    private CourseDto.CourseDateRequest courseDateRequest;
    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

    private CourseTime courseTime_2;
    private CourseDate courseDate_2;
    private Course course_2;

    private CourseDto.CourseDateRequest courseDateRequest_none;
    private CourseDate courseDate_none;

    private Enrollment enrollment_1;
    private Enrollment enrollment_2;
    private EnrollmentDto.applyRequest applyRequest;

    List<EnrollmentDto.Response> responses;

    @BeforeEach
    void setUp() {
        studentUserInfo_1 = UserInfo.builder()
                .name(STUDENT_1_NAME)
                .email(STUDENT_1_EMAIL)
                .phone(STUDENT_1_PHONE)
                .build();

        student_1 = Student.builder()
                .id(STUDENT_1_ID)
                .userInfo(studentUserInfo_1)
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

        courseDateRequest = CourseDto.CourseDateRequest.builder()
                .year(YEAR_2024)
                .month(MONTH_10)
                .day(DAY_31)
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
                .courseTime(courseTime_2)
                .professor(professor_2)
                .build();

        courseDateRequest_none = CourseDto.CourseDateRequest.builder()
                .year(9999)
                .month(12)
                .day(31)
                .build();

        courseDate_none = CourseDate.builder()
                .year(9999)
                .month(12)
                .day(31)
                .build();

        enrollment_1 = Enrollment.builder()
                .id(ENROLLMENT_1_ID)
                .course(course_1)
                .student(student_1)
                .build();

        enrollment_2 = Enrollment.builder()
                .id(ENROLLMENT_2_ID)
                .course(course_2)
                .student(student_1)
                .build();

        applyRequest = EnrollmentDto.applyRequest.builder()
                .courseId(COURSE_1_ID)
                .studentId(STUDENT_1_ID)
                .build();

        responses = List.of(
                EnrollmentDto.Response.of(enrollment_1),
                EnrollmentDto.Response.of(enrollment_2)
        );
    }

    @Nested
    @DisplayName("listEnrollments 메서드는")
    class Describe_listEnrollments {
        @Nested
        @DisplayName("특강 신청 목록이 존재하는 신청자 식별자가 주어진다면")
        class Context_WithExistedStudentId {
            private final Long EXISTED_ID = STUDENT_1_ID;

            @Test
            @DisplayName("해당 특강 신청 목록을 반환한다.")
            void itReturnsEnrollments() throws Exception {
                given(enrollmentFacade.listEnrollments(EXISTED_ID)).willReturn(responses);

                mockMvc.perform(
                                get("/api/enrollment/user/{id}", EXISTED_ID)
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$.[0].id").value(ENROLLMENT_1_ID))
                        .andExpect(jsonPath("$.[1].id").value(ENROLLMENT_2_ID));
            }
        }

        @Nested
        @DisplayName("특강 신청 목록이 존재하지 않는 신청자 식별자가 주어진다면")
        class Context_WithNotExistedStudentId {
            private final Long NOT_EXISTED_ID = NOT_EXISTED_STUDENT_ID;

            @Test
            @DisplayName("빈 리스트를 반환한다.")
            void itReturnsEmptyList() throws Exception {
                given(enrollmentFacade.listEnrollments(NOT_EXISTED_ID)).willReturn(List.of());

                mockMvc.perform(
                                get("/api/enrollment/user/{id}", NOT_EXISTED_ID)
                                        .accept(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(content().string("[]"));
            }
        }
    }

    @Nested
    @DisplayName("createEnrollment 메서드는")
    class Describe_createEnrollment {
        @Nested
        @DisplayName("존재하는 특강 식별자와 신청자 식별자가 주어진다면")
        class Context_WithExistedCourseIdAndExistedStudentId {
            private final Long EXISTED_COURSE_ID = COURSE_1_ID;
            private final Long EXISTED_STUDY_ID = STUDENT_1_ID;

            @Test
            @DisplayName("특강 신청 내역을 생성하고 반환한다.")
            void itCreatesEnrollmentAndReturnsEnrollment() throws Exception {
                given(enrollmentFacade.createEnrollment(EXISTED_COURSE_ID, EXISTED_STUDY_ID))
                        .willReturn(EnrollmentDto.Response.of(enrollment_1));

                mockMvc.perform(
                                post("/api/enrollment/apply/course/{courseId}/student/{studentId}",
                                        EXISTED_COURSE_ID, EXISTED_STUDY_ID
                                )
                                        .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(ENROLLMENT_1_ID));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 특강 식별자가 주어진다면")
        class Context_WithNotExistedCourseId {
            private final Long EXISTED_STUDY_ID = STUDENT_1_ID;

            @Test
            @DisplayName("특강을 찾을 수 없다는 예외를 반환한다.")
            void itThrowsNotFoundException() throws Exception {
                given(enrollmentFacade.createEnrollment(eq(NOT_EXISTED_COURSE_ID), eq(EXISTED_STUDY_ID)))
                        .willThrow(CourseNotFoundException.class);

                mockMvc.perform(
                                post("/api/enrollment/apply/course/{courseId}/student/{studentId}",
                                        NOT_EXISTED_COURSE_ID, EXISTED_STUDY_ID
                                )
                        )
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 신청자 식별자가 주어진다면")
        class Context_WithNotExistedStudentId {
            private final Long EXISTED_COURSE_ID = COURSE_1_ID;

            @Test
            @DisplayName("특강을 찾을 수 없다는 예외를 반환한다.")
            void itThrowsNotFoundException() throws Exception {
                given(enrollmentFacade.createEnrollment(eq(EXISTED_COURSE_ID), eq(NOT_EXISTED_STUDENT_ID)))
                        .willThrow(CourseNotFoundException.class);

                mockMvc.perform(
                                post("/api/enrollment/apply/course/{courseId}/student/{studentId}",
                                        EXISTED_COURSE_ID, NOT_EXISTED_STUDENT_ID
                                )
                        )
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }
    }
}