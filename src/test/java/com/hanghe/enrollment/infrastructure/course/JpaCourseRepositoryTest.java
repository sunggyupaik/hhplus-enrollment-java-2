package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseDate;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.course.CourseTime;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.infrastructure.professor.JpaProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaCourseRepositoryTest {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JpaProfessorRepository professorRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final Long PROFESSOR_1_ID = 10L;
    private static final String PROFESSOR_1_NAME = "강연자1";
    private static final String PROFESSOR_1_EMAIL = "1@naver.com";
    private static final String PROFESSOR_1_PHONE = "01011111111";

    private static final Long COURSE_1_ID = 1L;
    private static final String COURSE_1_TITLE = "코스1 특강";

    private CourseDto.CourseDateRequest courseDateRequest;

    private Professor professor_1;
    private UserInfo professorUserInfo_1;

    private CourseDate courseDate_1;
    private CourseTime courseTime_1;
    private Course course_1;


    @BeforeEach
    void setUp() {
        courseDateRequest = CourseDto.CourseDateRequest.builder()
                .year(2024)
                .month(10)
                .day(31)
                .build();

        professorUserInfo_1 = UserInfo.builder()
                .name(PROFESSOR_1_NAME)
                .email(PROFESSOR_1_EMAIL)
                .phone(PROFESSOR_1_PHONE)
                .build();

        professor_1 = Professor.builder()
                //.id(PROFESSOR_1_ID)
                .userInfo(professorUserInfo_1)
                .build();

        courseDate_1 = CourseDate.builder()
                .year(2024)
                .month(10)
                .day(31)
                .build();

        courseTime_1 = CourseTime.builder()
                .startTime(12)
                .startMinute(0)
                .endTime(14)
                .endMinute(0)
                .build();

        course_1 = Course.builder()
                //.id(COURSE_1_ID)
                .title(COURSE_1_TITLE)
                .courseDate(courseDate_1)
                .courseTime(courseTime_1)
                .professor(professor_1)
                .build();

        entityManager.persist(professor_1);
        entityManager.persist(course_1);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void lists() {
        //courseRepository.saveAndFlush(course_1);

        List<Course> courseTimes = courseRepository.findAllByCourseDate(courseDate_1);

        assertThat(courseTimes).hasSize(1);
    }
}