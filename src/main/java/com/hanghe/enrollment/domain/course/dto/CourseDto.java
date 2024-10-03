package com.hanghe.enrollment.domain.course.dto;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.user.professor.Professor;
import lombok.*;

import java.util.List;

public class CourseDto {
    @Getter
    @NoArgsConstructor
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
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private Professor professor;
        private List<CourseOption> courseOptions;

        public static Response of(Course course, List<CourseOption> courseOptions) {
            return Response.builder()
                    .id(course.getId())
                    .professor(course.getProfessor())
                    .courseOptions(courseOptions)
                    .build();
        }
    }
}
