package com.hanghe.enrollment.common.exception;

public class CourseOptionNotFoundException extends RuntimeException {
    public CourseOptionNotFoundException(Long id) {
        super("course option not found: " + id);
    }
}
