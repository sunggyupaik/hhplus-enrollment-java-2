package com.hanghe.enrollment.domain.course;

import java.util.List;

public interface CourseReader {
    Course getCourse(Long courseId);

    List<Course> getCourses(CourseDate courseDate);
}
