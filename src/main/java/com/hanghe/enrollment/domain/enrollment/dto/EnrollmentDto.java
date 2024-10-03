package com.hanghe.enrollment.domain.enrollment.dto;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.user.student.Student;
import lombok.*;

public class EnrollmentDto {
    @Getter
    @NoArgsConstructor
    @ToString
    public static class applyRequest {
        private Long studentId;
        private Long courseId;
        private Long courseOptionId;

        @Builder
        public applyRequest(Long studentId, Long courseId, Long courseOptionId) {
            this.studentId = studentId;
            this.courseId = courseId;
            this.courseOptionId = courseOptionId;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Response {
        private Long id;
        private Student student;
        private Course course;

        public static Response of(Enrollment enrollment) {
            return Response.builder()
                    .id(enrollment.getId())
                    .student(enrollment.getStudent())
                    .course(enrollment.getCourse())
                    .build();
        }
    }
}
