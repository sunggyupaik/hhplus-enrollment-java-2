package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.common.exception.CourseNotFoundException;
import com.hanghe.enrollment.common.exception.CourseOptionNotFoundException;
import com.hanghe.enrollment.common.exception.StudentNotFoundException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseFixture;
import com.hanghe.enrollment.domain.course.CourseReader;
import com.hanghe.enrollment.domain.course.dto.CourseDto.CourseDateRequest;
import com.hanghe.enrollment.domain.course.option.*;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.UserInfoFixture;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.professor.ProfessorFixture;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentFixture;
import com.hanghe.enrollment.domain.user.student.StudentReader;
import com.hanghe.enrollment.infrastructure.course.CourseReaderImpl;
import com.hanghe.enrollment.infrastructure.enrollment.EnrollmentReaderImpl;
import com.hanghe.enrollment.infrastructure.enrollment.EnrollmentStoreImpl;
import com.hanghe.enrollment.infrastructure.student.StudentReaderImpl;
import com.hanghe.enrollment.interfaces.course.option.CourseOptionFixture;
import com.hanghe.enrollment.interfaces.enrollment.EnrollmentFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EnrollmentServiceImplTest {
    private EnrollmentService enrollmentService;
    private EnrollmentReader enrollmentReader;
    private EnrollmentStore enrollmentStore;
    private CourseOptionReader courseOptionReader;
    private CourseReader courseReader;
    private StudentReader studentReader;

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
    private static final Long NOT_EXISTED_COURSE_OPTION_ID = 997L;
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

    private CourseDateRequest courseDateRequest;
    private CourseOption courseOption_1;
    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

    private CourseOption courseOption_2;
    private CourseTime courseTime_2;
    private CourseDate courseDate_2;
    private Course course_2;

    private CourseDateRequest courseDateRequest_none;
    private CourseDate courseDate_none;

    private Enrollment enrollment_1;
    private Enrollment enrollment_2;
    private EnrollmentDto.applyRequest applyRequest;

    @BeforeEach
    void setUp() {
        enrollmentReader = mock(EnrollmentReaderImpl.class);
        enrollmentStore = mock(EnrollmentStoreImpl.class);
        courseReader = mock(CourseReaderImpl.class);
        courseOptionReader = mock(CourseOptionReader.class);
        studentReader = mock(StudentReaderImpl.class);
        enrollmentService = new EnrollmentServiceImpl(
                enrollmentReader, enrollmentStore, courseReader, courseOptionReader, studentReader
        );

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
        course_1.addCourseOption(courseOption_1);

        courseDate_2 = CourseOptionFixture.createCourseDate(YEAR_2024, MONTH_10, DAY_31);
        courseTime_2 = CourseOptionFixture.createCourseTime(14, 0, 16, 0);
        courseOption_2 = CourseOptionFixture.createCourseOption(COURSE_OPTION_2_ID, courseDate_2, courseTime_2);
        course_2 = CourseFixture.createCourse(COURSE_2_ID, COURSE_2_TITLE, professor_2);
        course_2.addCourseOption(courseOption_2);

        enrollment_1 = EnrollmentFixture.createEnrollment(ENROLLMENT_1_ID, course_1, student_1);
        enrollment_2 = EnrollmentFixture.createEnrollment(ENROLLMENT_2_ID, course_2, student_1);

        courseDateRequest_none = CourseDateRequest.builder()
                .year(9999)
                .month(12)
                .day(31)
                .build();

        courseDate_none = CourseDate.builder()
                .year(9999)
                .month(12)
                .day(31)
                .build();

        applyRequest = EnrollmentDto.applyRequest.builder()
                .courseId(COURSE_1_ID)
                .studentId(STUDENT_1_ID)
                .build();
    }

    @Test
    @DisplayName("주어진 신청자 식별자에 해당하는 신청 내역 목록을 조회하여 반환한다.")
    void getEnrollmentsWithExistedStudentId() {
        given(enrollmentReader.getEnrollments(STUDENT_1_ID)).willReturn(List.of(enrollment_1, enrollment_2));

        List<EnrollmentDto.Response> enrollments = enrollmentService.getEnrollments(STUDENT_1_ID);

        assertThat(enrollments).hasSize(2);
        assertThat(enrollments.get(0).getCourse().getId()).isEqualTo(COURSE_1_ID);
        assertThat(enrollments.get(0).getStudent().getId()).isEqualTo(STUDENT_1_ID);
        assertThat(enrollments.get(0).getCourse().getProfessor().getId()).isEqualTo(PROFESSOR_1_ID);
    }

    @Test
    @DisplayName("주어진 신청자 식별자에 해당하는 신청 내역이 없으면 빈 리스트를 반환한다.")
    void getEnrollmentsWithNotExistedStudentId() {
        given(enrollmentReader.getEnrollments(NOT_EXISTED_STUDENT_ID)).willReturn(List.of());

        List<EnrollmentDto.Response> enrollments = enrollmentService.getEnrollments(NOT_EXISTED_STUDENT_ID);

        assertThat(enrollments).hasSize(0);
    }

    @Test
    @DisplayName("주어진 신청자 식별자와 강의 식별자로 신청 내역을 생성하고 반환한다.")
    void createWithExistedStudentIdAndExistedCourseId() {
        given(studentReader.getStudent(STUDENT_1_ID)).willReturn(student_1);
        given(courseReader.getCourse(COURSE_1_ID)).willReturn(course_1);
        given(courseOptionReader.getByIdForPessimistLock(COURSE_1_ID, COURSE_OPTION_1_ID)).willReturn(courseOption_1);
        given(enrollmentStore.store(any(Enrollment.class))).willReturn(enrollment_1);

        EnrollmentDto.Response createdEnrollment = enrollmentService.apply(STUDENT_1_ID, COURSE_1_ID, COURSE_OPTION_1_ID);

        assertThat(createdEnrollment.getId()).isEqualTo(ENROLLMENT_1_ID);
        assertThat(createdEnrollment.getCourse().getId()).isEqualTo(COURSE_1_ID);
        assertThat(createdEnrollment.getStudent().getId()).isEqualTo(STUDENT_1_ID);
    }

    @Test
    @DisplayName("주어진 신청자 식별자가 존재하지 않으면 찾을 수 없다는 예외를 반환한다.")
    void createWithNotExistedStudentId_throwsNotFoundException() {
        given(studentReader.getStudent(NOT_EXISTED_STUDENT_ID)).willThrow(StudentNotFoundException.class);

        assertThatThrownBy(
                () -> enrollmentService.apply(NOT_EXISTED_STUDENT_ID, COURSE_1_ID, COURSE_OPTION_1_ID)
        )
                .isInstanceOf(StudentNotFoundException.class);
    }

    @Test
    @DisplayName("주어진 특강 식별자가 존재하지 않으면 찾을 수 없다는 예외를 반환한다.")
    void createWithNotExistedCourseId_throwsNotFoundException() {
        given(studentReader.getStudent(STUDENT_1_ID)).willReturn(student_1);
        given(courseReader.getCourse(eq(NOT_EXISTED_COURSE_ID)))
                .willThrow(CourseNotFoundException.class);

        assertThatThrownBy(
                () -> enrollmentService.apply(STUDENT_1_ID, NOT_EXISTED_COURSE_ID, COURSE_OPTION_1_ID)
        )
                .isInstanceOf(CourseNotFoundException.class);
    }

    @Test
    @DisplayName("주어진 특강 옵션 식별자가 존재하지 않으면 찾을 수 없다는 예외를 반환한다.")
    void createWithNotExistedCourseOptionId_throwsNotFoundException() {
        given(studentReader.getStudent(STUDENT_1_ID)).willReturn(student_1);
        given(courseReader.getCourse(NOT_EXISTED_COURSE_ID)).willReturn(course_1);
        given(courseOptionReader.getByIdForPessimistLock(COURSE_1_ID, NOT_EXISTED_COURSE_OPTION_ID))
                .willThrow(CourseOptionNotFoundException.class);

        assertThatThrownBy(
                () -> enrollmentService.apply(STUDENT_1_ID, COURSE_1_ID, NOT_EXISTED_COURSE_OPTION_ID)
        )
                .isInstanceOf(CourseOptionNotFoundException.class);
    }
}
