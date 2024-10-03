package com.hanghe.enrollment.domain.course.option;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CourseTime {
    private Integer startTime;
    private Integer startMinute;
    private Integer endTime;
    private Integer endMinute;

    @Builder
    public CourseTime(
            Integer startTime,
            Integer startMinute,
            Integer endTime,
            Integer endMinute
    ) {
        this.startTime = startTime;
        this.startMinute = startMinute;
        this.endTime = endTime;
        this.endMinute = endMinute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseTime that = (CourseTime) o;
        return Objects.equals(getStartTime(), that.getStartTime())
                && Objects.equals(getStartMinute(), that.getStartMinute())
                && Objects.equals(getEndTime(), that.getEndTime())
                && Objects.equals(getEndMinute(), that.getEndMinute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTime(), getStartMinute(), getEndTime(), getEndMinute());
    }
}
