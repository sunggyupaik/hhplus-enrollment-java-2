package com.hanghe.enrollment.common.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("course not found: " + id);
    }
}
