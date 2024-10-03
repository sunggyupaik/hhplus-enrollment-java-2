package com.hanghe.enrollment.interfaces.enrollment;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.user.student.Student;

public class EnrollmentFixture {
    public static Enrollment createEnrollment(Long id, Course course, Student student) {
        return Enrollment.builder()
                .id(id)
                .course(course)
                .student(student)
                .build();
    }
}
