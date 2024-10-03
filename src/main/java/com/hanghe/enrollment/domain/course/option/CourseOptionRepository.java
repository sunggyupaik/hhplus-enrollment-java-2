package com.hanghe.enrollment.domain.course.option;

import java.util.List;

public interface CourseOptionRepository {
    List<CourseOption> findAllByCourseDate(CourseDate courseDate);
}
