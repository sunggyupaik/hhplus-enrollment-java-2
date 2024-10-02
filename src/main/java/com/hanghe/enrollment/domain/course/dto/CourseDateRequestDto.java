package com.hanghe.enrollment.domain.course.dto;

import com.hanghe.enrollment.domain.course.CourseDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDateRequestDto {
    private Integer year;
    private Integer month;
    private Integer day;

    @Builder
    public CourseDateRequestDto(
            Integer year,
            Integer month,
            Integer day
    ) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public CourseDate toCourseDate() {
        return CourseDate.builder()
                .year(year)
                .month(month)
                .day(day)
                .build();
    }
}
