package com.hanghe.enrollment.domain.course.dto;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseDate;
import com.hanghe.enrollment.domain.course.CourseStatus;
import com.hanghe.enrollment.domain.user.professor.Professor;
import lombok.*;

public class CourseDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CourseDateRequest {
        private Integer year;
        private Integer month;
        private Integer day;

        @Builder
        public CourseDateRequest(
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long id;
        private Integer year;
        private Integer month;
        private Integer day;
        private Integer startTime;
        private Integer startMinute;
        private Integer endTime;
        private Integer endMinute;
        private CourseStatus status;
        private Professor professor;

        public static Response of(Course course) {
            return Response.builder()
                    .id(course.getId())
                    .year(course.getCourseDate().getYear())
                    .month(course.getCourseDate().getMonth())
                    .day(course.getCourseDate().getDay())
                    .startTime(course.getCourseTime().getStartTime())
                    .startMinute(course.getCourseTime().getStartMinute())
                    .endTime(course.getCourseTime().getEndTime())
                    .endMinute(course.getCourseTime().getEndMinute())
                    .status(course.getStatus())
                    .professor(course.getProfessor())
                    .build();
        }
    }
}
