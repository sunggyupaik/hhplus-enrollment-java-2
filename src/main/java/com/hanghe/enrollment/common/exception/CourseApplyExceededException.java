package com.hanghe.enrollment.common.exception;

public class CourseApplyExceededException extends RuntimeException {
    public CourseApplyExceededException(Long courseOptionId) {
        super("course apply exceeded: " + courseOptionId);
    }
}
