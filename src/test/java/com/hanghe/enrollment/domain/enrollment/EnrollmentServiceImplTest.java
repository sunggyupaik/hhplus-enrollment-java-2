package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseDate;
import com.hanghe.enrollment.domain.course.CourseTime;
import com.hanghe.enrollment.domain.course.dto.CourseDateRequestDto;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.infrastructure.enrollment.JpaEnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EnrollmentServiceImplTest {
    private EnrollmentService enrollmentService;
    private EnrollmentRepository enrollmentRepository;

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

    private CourseDateRequestDto courseDateRequestDto;
    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

    private CourseTime courseTime_2;
    private CourseDate courseDate_2;
    private Course course_2;

    private CourseDateRequestDto courseDateRequestDto_none;
    private CourseDate courseDate_none;

    private Enrollment enrollment_1;
    private Enrollment enrollment_2;

    @BeforeEach
    void setUp() {
        enrollmentRepository = mock(JpaEnrollmentRepository.class);
        enrollmentService = new EnrollmentServiceImpl(enrollmentRepository);

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

        courseDateRequestDto = CourseDateRequestDto.builder()
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

        courseDateRequestDto_none = CourseDateRequestDto.builder()
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
    }

    @Test
    @DisplayName("주어진 신청자 식별자에 해당하는 신청 내역 목록을 조회하여 반환한다.")
    void detailWithExistedUserId() {
        given(enrollmentRepository.findByStudentId(STUDENT_1_ID)).willReturn(List.of(enrollment_1, enrollment_2));

        List<Enrollment> enrollments = enrollmentService.getEnrollment(STUDENT_1_ID);

        assertThat(enrollments).hasSize(2);
    }
}