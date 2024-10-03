package com.hanghe.enrollment.interfaces.enrollment;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.user.student.Student;

public class EnrollmentFixture {
    public static Enrollment createEnrollment(Long id, Course course, CourseOption courseOption, Student student) {
        return Enrollment.builder()
                .id(id)
                .course(course)
                .courseOption(courseOption)
                .student(student)
                .build();
    }
}
