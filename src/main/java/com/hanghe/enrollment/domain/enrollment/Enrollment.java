package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.common.BaseTimeEntity;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.user.student.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrollment extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Builder
    public Enrollment(Long id, Course course, Student student) {
        this.id = id;
        this.course = course;
        this.student = student;
    }
}
