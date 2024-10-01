package com.hanghe.enrollment.domain.course;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseTime {
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer startTime;
    private Integer startMinute;
    private Integer endTime;
    private Integer endMinute;

    @Builder
    public CourseTime(
            Integer year,
            Integer month,
            Integer day,
            Integer startTime,
            Integer startMinute,
            Integer endTime,
            Integer endMinute
    ) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.startTime = startTime;
        this.startMinute = startMinute;
        this.endTime = endTime;
        this.endMinute = endMinute;
    }
}
