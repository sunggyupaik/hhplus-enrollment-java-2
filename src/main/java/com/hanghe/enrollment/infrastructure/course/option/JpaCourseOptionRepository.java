package com.hanghe.enrollment.infrastructure.course.option;

import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseOptionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCourseOptionRepository
        extends CourseOptionRepository, JpaRepository<CourseOption, Long> {
    List<CourseOption> findAllByCourseDate(CourseDate courseDate);
}
