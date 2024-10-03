package com.hanghe.enrollment.domain.user.student;

import com.hanghe.enrollment.domain.user.UserInfo;

public class StudentFixture {
    public static Student createStudent(Long id, UserInfo userInfo) {
        return Student.builder()
                .id(id)
                .userInfo(userInfo)
                .build();
    }
}
