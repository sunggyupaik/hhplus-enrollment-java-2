package com.hanghe.enrollment.domain.course;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseStatus {
    OPEN("모집중"),
    CLOSE("마감");

    private final String description;
}
