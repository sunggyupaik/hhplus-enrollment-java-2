package com.hanghe.enrollment.common.exception;

public class EnrollmentAlreadyExistsException extends RuntimeException {
    public EnrollmentAlreadyExistsException(Long studentId, Long courseId, Long courseOptionId) {
        super("enrollment already exists exception, studentId: "
                + studentId + ", courseId: " + courseId + ", courseOptionId: " + courseOptionId);
    }
}
