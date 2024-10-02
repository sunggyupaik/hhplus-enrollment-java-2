package com.hanghe.enrollment.domain.course;

import java.util.List;

public interface CourseRepository {
    List<Course> findAllByCourseDate(CourseDate courseDate);
}
