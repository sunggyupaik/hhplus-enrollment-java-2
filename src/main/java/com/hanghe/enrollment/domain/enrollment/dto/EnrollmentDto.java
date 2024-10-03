package com.hanghe.enrollment.domain.enrollment.dto;

import lombok.*;

public class EnrollmentDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class applyRequest {
        private Long studentId;
        private Long courseId;

        @Builder
        public applyRequest(Long studentId, Long courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }
    }
}
