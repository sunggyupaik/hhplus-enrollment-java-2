package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.common.BaseTimeEntity;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.user.professor.Professor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Course extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private Integer capacity = 30;

    private String title;

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    private List<CourseOption> courseOptions = new ArrayList<>();

    @Builder
    public Course(
            Long id,
            String title,
            Professor professor
    ) {
        this.id = id;
        this.title = title;
        this.professor = professor;
    }

    public void addCourseOption(CourseOption courseOption) {
        this.courseOptions.add(courseOption);
        courseOption.changeCourse(this);
    }
}
