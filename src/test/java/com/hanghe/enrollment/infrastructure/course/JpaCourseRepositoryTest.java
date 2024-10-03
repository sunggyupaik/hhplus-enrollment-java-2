package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseFixture;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.course.dto.CourseDto;
import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseTime;
import com.hanghe.enrollment.domain.user.UserInfo;
import com.hanghe.enrollment.domain.user.UserInfoFixture;
import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.professor.ProfessorFixture;
import com.hanghe.enrollment.infrastructure.professor.JpaProfessorRepository;
import com.hanghe.enrollment.interfaces.course.option.CourseOptionFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
    private static final Long COURSE_OPTION_1_ID = 50L;
    private static final String COURSE_1_TITLE = "코스1 특강";

    private CourseDto.CourseDateRequest courseDateRequest;

    private Professor professor_1;
    private UserInfo professorUserInfo_1;

    private CourseOption courseOption_1;
    private CourseTime courseTime_1;
    private CourseDate courseDate_1;
    private Course course_1;


    @BeforeEach
    void setUp() {
        courseDateRequest = CourseDto.CourseDateRequest.builder()
                .year(2024)
                .month(10)
                .day(31)
                .build();

        professorUserInfo_1 = UserInfoFixture.createUserInfo(PROFESSOR_1_NAME, PROFESSOR_1_EMAIL, PROFESSOR_1_PHONE);
        professor_1 = ProfessorFixture.createProfessor(null, professorUserInfo_1);

        courseDate_1 = CourseOptionFixture.createCourseDate(2024, 10, 31);
        courseTime_1 = CourseOptionFixture.createCourseTime(12, 0, 14, 0);
        courseOption_1 = CourseOptionFixture.createCourseOption(null, courseDate_1, courseTime_1);
        course_1 = CourseFixture.createCourse(null, COURSE_1_TITLE, professor_1);
        course_1.addCourseOption(courseOption_1);
        //courseOption_1.changeCourse(course_1);

        entityManager.persist(professor_1);
        entityManager.persist(course_1);
        entityManager.persist(courseOption_1);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void lists() {
        //courseRepository.saveAndFlush(course_1);

        Course course = courseRepository.findById(1L).get();

        assertThat(course.getId()).isEqualTo(1L);
        assertThat(course.getCourseOptions().get(0).getId()).isEqualTo(1L);
    }
}