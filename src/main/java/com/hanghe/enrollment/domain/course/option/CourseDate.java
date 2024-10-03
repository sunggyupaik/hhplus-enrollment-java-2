package com.hanghe.enrollment.domain.course.option;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CourseDate {
    private Integer year;
    private Integer month;
    private Integer day;

    @Builder
    public CourseDate(
            Integer year,
            Integer month,
            Integer day
    ) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDate that = (CourseDate) o;
        return Objects.equals(getYear(), that.getYear())
                && Objects.equals(getMonth(), that.getMonth())
                && Objects.equals(getDay(), that.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getYear(), getMonth(), getDay());
    }
}
