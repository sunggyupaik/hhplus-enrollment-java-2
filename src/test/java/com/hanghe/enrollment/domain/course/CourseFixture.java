package com.hanghe.enrollment.domain.course;

import com.hanghe.enrollment.domain.user.professor.Professor;

public class CourseFixture {
    public static Course createCourse(Long id, String title, Professor professor) {
        return Course.builder()
                .id(id)
                .title(title)
                .professor(professor)
                .build();
    }
}
