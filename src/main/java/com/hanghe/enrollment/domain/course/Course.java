package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.common.BaseTimeEntity;
import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.user.professor.Professor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private Integer capacity = 30;

    private String title;

    @Embedded
    private CourseTime courseTime;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="professor_id")
    private Professor professor;
}
