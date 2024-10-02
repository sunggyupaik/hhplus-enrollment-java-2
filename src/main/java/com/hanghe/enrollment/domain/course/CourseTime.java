package com.hanghe.enrollment.domain.course;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
