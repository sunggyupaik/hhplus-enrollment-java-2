package com.hanghe.enrollment.common.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long studentId) {
        super("student not found: " + studentId);
    }
}
