package com.hanghe.enrollment.domain.course;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
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
}
