package com.hanghe.enrollment.domain.course.option;

import java.util.List;
import java.util.Optional;

public interface CourseOptionRepository {
    List<CourseOption> findAllByCourseDate(CourseDate courseDate);

    Optional<CourseOption> findByIdForPessimistLock(Long courseId, Long courseOptionId);

    Optional<CourseOption> findById(Long courseOptionId);

    CourseOption saveAndFlush(CourseOption courseOption);
}
