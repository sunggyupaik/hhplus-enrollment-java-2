package com.hanghe.enrollment.domain.course.option;

public interface CourseOptionReader {
    CourseOption getByIdForPessimistLock(Long courseId, Long courseOptionId);

    CourseOption getCourseOption(Long courseOptionId);
}
