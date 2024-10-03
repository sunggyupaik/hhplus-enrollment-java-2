package com.hanghe.enrollment.interfaces.enrollment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghe.enrollment.application.enrollment.EnrollmentFacade;
import com.hanghe.enrollment.common.exception.CourseNotFoundException;
import com.hanghe.enrollment.common.exception.CourseOptionNotFoundException;
import com.hanghe.enrollment.common.exception.EnrollmentAlreadyExistsException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseFixture;
import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseTime;
import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.UserInfoFixture;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.professor.ProfessorFixture;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentFixture;
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
    private static final Long NOT_EXISTED_COURSE_OPTION_ID = 998L;
    private static final Long COURSE_1_ID = 1L;
    private static final Long COURSE_OPTION_1_ID = 50L;
    private static final String COURSE_1_TITLE = "코스1 특강";

    private static final Long COURSE_2_ID = 2L;
    private static final Long COURSE_OPTION_2_ID = 51L;
    private static final String COURSE_2_TITLE = "코스2 특강";

    private static final Long ENROLLMENT_1_ID = 30L;
    private static final Long ENROLLMENT_2_ID = 31L;

    private Student student_1;
    private UserInfo studentUserInfo_1;

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

    private Enrollment enrollment_1;
    private Enrollment enrollment_2;

    List<EnrollmentDto.Response> responses;

    @BeforeEach
    void setUp() {
        studentUserInfo_1 = UserInfoFixture.createUserInfo(STUDENT_1_NAME, STUDENT_1_EMAIL, STUDENT_1_PHONE);
        student_1 = StudentFixture.createStudent(STUDENT_1_ID, studentUserInfo_1);

        professorUserInfo_1 = UserInfoFixture.createUserInfo(PROFESSOR_1_NAME, PROFESSOR_1_EMAIL, PROFESSOR_1_PHONE);
        professor_1 = ProfessorFixture.createProfessor(PROFESSOR_1_ID, professorUserInfo_1);

        professorUserInfo_2 = UserInfoFixture.createUserInfo(PROFESSOR_2_NAME, PROFESSOR_2_EMAIL, PROFESSOR_2_PHONE);
        professor_2 = ProfessorFixture.createProfessor(PROFESSOR_2_ID, professorUserInfo_2);

        courseDate_1 = CourseOptionFixture.createCourseDate(YEAR_2024, MONTH_10, DAY_31);
        courseTime_1 = CourseOptionFixture.createCourseTime(12, 0, 14, 0);
        courseOption_1 = CourseOptionFixture.createCourseOption(COURSE_OPTION_1_ID, courseDate_1, courseTime_1);
        course_1 = CourseFixture.createCourse(COURSE_1_ID, COURSE_1_TITLE, professor_1);

        courseDate_2 = CourseOptionFixture.createCourseDate(YEAR_2024, MONTH_10, DAY_31);
        courseTime_2 = CourseOptionFixture.createCourseTime(14, 0, 16, 0);
        courseOption_2 = CourseOptionFixture.createCourseOption(COURSE_OPTION_2_ID, courseDate_2, courseTime_2);
        course_2 = CourseFixture.createCourse(COURSE_2_ID, COURSE_2_TITLE, professor_2);

        enrollment_1 = EnrollmentFixture.createEnrollment(ENROLLMENT_1_ID, course_1, courseOption_1, student_1);
        enrollment_2 = EnrollmentFixture.createEnrollment(ENROLLMENT_2_ID, course_2, courseOption_2, student_1);

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
        @DisplayName("존재하는 특강 식별자, 옵션 식별자, 신청자 식별자가 주어진다면")
        class Context_WithExistedCourseIdAndExistedStudentId {
            private final Long EXISTED_STUDENT_ID = STUDENT_1_ID;
            private final Long EXISTED_COURSE_ID = COURSE_1_ID;
            private final Long EXISTED_COURSE_OPTION_ID = COURSE_OPTION_1_ID;

            private EnrollmentDto.applyRequest applyRequest = EnrollmentDto.applyRequest.builder()
                    .studentId(EXISTED_STUDENT_ID)
                    .courseId(EXISTED_COURSE_ID)
                    .courseOptionId(EXISTED_COURSE_OPTION_ID)
                    .build();

            @Test
            @DisplayName("특강 신청 내역을 생성하고 반환한다.")
            void itCreatesEnrollmentAndReturnsEnrollment() throws Exception {
                given(enrollmentFacade.createEnrollment(any(EnrollmentDto.applyRequest.class)))
                        .willReturn(EnrollmentDto.Response.of(enrollment_1));

                mockMvc.perform(
                                post("/api/enrollment/apply")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(applyRequest))
                        )
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(ENROLLMENT_1_ID));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 특강 식별자가 주어진다면")
        class Context_WithNotExistedCourseId {
            private EnrollmentDto.applyRequest applyRequest = EnrollmentDto.applyRequest.builder()
                    .studentId(STUDENT_1_ID)
                    .courseId(NOT_EXISTED_COURSE_ID)
                    .courseOptionId(COURSE_OPTION_1_ID)
                    .build();

            @Test
            @DisplayName("특강을 찾을 수 없다는 예외를 반환한다.")
            void itThrowsNotFoundException() throws Exception {
                given(enrollmentFacade.createEnrollment(any(EnrollmentDto.applyRequest.class)))
                        .willThrow(CourseNotFoundException.class);

                mockMvc.perform(
                                post("/api/enrollment/apply")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(applyRequest))
                        )
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 특강 옵션 식별자가 주어진다면")
        class Context_WithNotExistedCourseOptionId {
            private EnrollmentDto.applyRequest applyRequest = EnrollmentDto.applyRequest.builder()
                    .studentId(STUDENT_1_ID)
                    .courseId(COURSE_1_ID)
                    .courseOptionId(NOT_EXISTED_COURSE_OPTION_ID)
                    .build();

            @Test
            @DisplayName("특강 옵션을 찾을 수 없다는 예외를 반환한다.")
            void itThrowsNotFoundException() throws Exception {
                given(enrollmentFacade.createEnrollment(any(EnrollmentDto.applyRequest.class)))
                        .willThrow(CourseOptionNotFoundException.class);

                mockMvc.perform(
                                post("/api/enrollment/apply")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(applyRequest))
                        )
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 신청자 식별자가 주어진다면")
        class Context_WithNotExistedStudentId {
            private EnrollmentDto.applyRequest applyRequest = EnrollmentDto.applyRequest.builder()
                    .studentId(NOT_EXISTED_STUDENT_ID)
                    .courseId(COURSE_1_ID)
                    .courseOptionId(COURSE_OPTION_1_ID)
                    .build();

            @Test
            @DisplayName("특강을 찾을 수 없다는 예외를 반환한다.")
            void itThrowsNotFoundException() throws Exception {
                given(enrollmentFacade.createEnrollment(any(EnrollmentDto.applyRequest.class)))
                        .willThrow(CourseNotFoundException.class);

                mockMvc.perform(
                                post("/api/enrollment/apply")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(applyRequest))
                        )
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("주어진 식별자에 해당하는 내역이 존재한다면")
        class Context_WithExistedIds {
            private EnrollmentDto.applyRequest applyRequest = EnrollmentDto.applyRequest.builder()
                    .studentId(STUDENT_1_ID)
                    .courseId(COURSE_1_ID)
                    .courseOptionId(COURSE_OPTION_1_ID)
                    .build();

            @Test
            @DisplayName("특강을 찾을 수 없다는 예외를 반환한다.")
            void itThrowsNotFoundException() throws Exception {
                given(enrollmentFacade.createEnrollment(any(EnrollmentDto.applyRequest.class)))
                        .willThrow(EnrollmentAlreadyExistsException.class);

                mockMvc.perform(
                                post("/api/enrollment/apply")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(applyRequest))
                        )
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }
    }
}