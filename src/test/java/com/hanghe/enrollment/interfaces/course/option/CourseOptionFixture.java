package com.hanghe.enrollment.interfaces.course.option;

import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseTime;

public class CourseOptionFixture {
    public static CourseOption createCourseOption(Long id, CourseDate courseDate, CourseTime courseTime) {
        return CourseOption.builder()
                .id(id)
                .courseDate(courseDate)
                .courseTime(courseTime)
                .build();
    }

    public static CourseDate createCourseDate(Integer year, Integer month, Integer day) {
        return CourseDate.builder()
                .year(year)
                .month(month)
                .day(day)
                .build();
    }

    public static CourseTime createCourseTime(
            Integer startTime, Integer startMinute, Integer endTime, Integer endMinute
    ) {
        return CourseTime.builder()
                .startTime(startTime)
                .startMinute(startMinute)
                .endTime(endTime)
                .endMinute(endMinute)
                .build();
    }
}
