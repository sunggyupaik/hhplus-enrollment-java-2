package com.hanghe.enrollment.domain.enrollment.dto;

import lombok.*;

public class EnrollmentDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class applyRequest {
        private Long userId;
        private Long courseId;

        @Builder
        public applyRequest(Long userId, Long courseId) {
            this.userId = userId;
            this.courseId = courseId;
        }
    }
}
