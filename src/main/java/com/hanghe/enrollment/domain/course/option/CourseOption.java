package com.hanghe.enrollment.domain.course.option;

import com.hanghe.enrollment.common.exception.CourseApplyExceededException;
import com.hanghe.enrollment.domain.course.Course;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CourseOption {
    private static final Integer MAX_APPLY = 30;

    @Id @GeneratedValue
    @Column(name = "course_option_id")
    private Long id;

    private Integer applyCount;

    @Embedded
    private CourseDate courseDate;

    @Embedded
    private CourseTime courseTime;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    private Course course;

    @Builder
    public CourseOption(
            Long id,
            CourseDate courseDate,
            CourseTime courseTime
    ) {
        this.id = id;
        this.applyCount = 0;
        this.courseDate = courseDate;
        this.courseTime = courseTime;
        this.status = CourseStatus.OPEN;
    }

    public void changeCourse(Course course) {
        this.course = course;
    }

    public void increaseApplyCount() {
        applyCount++;
        if (applyCount > MAX_APPLY) {
            throw new CourseApplyExceededException(id);
        }
    }
}
