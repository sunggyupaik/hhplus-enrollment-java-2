package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.common.exception.CourseApplyExceededException;
import com.hanghe.enrollment.common.exception.EnrollmentAlreadyExistsException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseFixture;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseTime;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.UserInfoFixture;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.professor.ProfessorFixture;
import com.hanghe.enrollment.domain.user.professor.ProfessorRepository;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentFixture;
import com.hanghe.enrollment.domain.user.student.StudentRepository;
import com.hanghe.enrollment.interfaces.course.option.CourseOptionFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EnrollmentConcurrencyTest {
    private final EnrollmentService enrollmentService;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;

    public EnrollmentConcurrencyTest(
            @Autowired EnrollmentService enrollmentService,
            @Autowired StudentRepository studentRepository,
            @Autowired ProfessorRepository professorRepository,
            @Autowired CourseRepository courseRepository
            ) {
        this.enrollmentService = enrollmentService;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
    }

    private static final Long PROFESSOR_1_ID = 10L;
    private static final String PROFESSOR_1_NAME = "강연자1";
    private static final String PROFESSOR_1_EMAIL = "강연자1@naver.com";
    private static final String PROFESSOR_1_PHONE = "01011111111";

    private static final String STUDENT_1_NAME = "신청자1";
    private static final String STUDENT_1_EMAIL = "신청자1@naver.com";
    private static final String STUDENT_1_PHONE = "01020202020";

    private static final String COURSE_1_TITLE = "코스1 특강";

    private UserInfo professorUserInfo_1;
    private Professor professor_1;

    private UserInfo studentUserInfo;
    private Student student;

    private CourseOption courseOption_1;
    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;

    @BeforeEach
    void setUp() {
        professorUserInfo_1 = UserInfoFixture.createUserInfo(PROFESSOR_1_NAME, PROFESSOR_1_EMAIL, PROFESSOR_1_PHONE);
        professor_1 = ProfessorFixture.createProfessor(PROFESSOR_1_ID, professorUserInfo_1);
        Professor createdProfessor = professorRepository.save(professor_1);

        courseDate_1 = CourseOptionFixture.createCourseDate(2024, 10, 31);
        courseTime_1 = CourseOptionFixture.createCourseTime(12, 0, 14, 0);
        courseOption_1 = CourseOptionFixture.createCourseOption(null, courseDate_1, courseTime_1);
        course_1 = CourseFixture.createCourse(null, COURSE_1_TITLE, createdProfessor);
        course_1.addCourseOption(courseOption_1);

        Course createdCourse = courseRepository.save(course_1);

        studentUserInfo = UserInfoFixture.createUserInfo(STUDENT_1_NAME, STUDENT_1_EMAIL, STUDENT_1_PHONE);

        for (int i = 1; i <= 40; i++) {
            student = StudentFixture.createStudent((long) i, studentUserInfo);
            studentRepository.save(student);
        }
    }

    @Test
    @DisplayName("서로 다른 신청자가 같은 특강을 40번 동시에 신청하면 10명은 실패한다.")
    void concurrentApplyForSameCourse40Times() throws InterruptedException {
        final int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger fail = new AtomicInteger(0);

        for (int i = 1; i <= threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    enrollmentService.apply((long) finalI, 1L, 1L);
                    success.incrementAndGet();
                } catch (CourseApplyExceededException e) {
                    fail.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        assertThat(success.get()).isEqualTo(30);
        assertThat(fail.get()).isEqualTo(10);
        assertThat(enrollmentService.lists().size()).isEqualTo(30);
    }

    @Test
    @DisplayName("동일한 신청자가 같은 특강을 5번 동시에 신청하면 1번 성공한다.")
    void concurrentApplyForSameCourseForSameStudent5Times() throws InterruptedException {
        final int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger fail = new AtomicInteger(0);

        for (int i = 1; i <= threadCount; i++) {
            executorService.submit(() -> {
                try {
                    enrollmentService.apply(1L, 1L, 1L);
                    success.incrementAndGet();
                } catch (EnrollmentAlreadyExistsException e) {
                    fail.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        assertThat(success.get()).isEqualTo(1);
        assertThat(fail.get()).isEqualTo(4);
        assertThat(enrollmentService.lists().size()).isEqualTo(1);
    }
}
