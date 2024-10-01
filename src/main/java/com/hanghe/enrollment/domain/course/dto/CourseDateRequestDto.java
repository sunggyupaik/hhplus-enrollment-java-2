package com.hanghe.enrollment.domain.course.dto;

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
}
