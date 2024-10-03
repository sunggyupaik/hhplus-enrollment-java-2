package com.hanghe.enrollment.domain.user.professor;

import com.hanghe.enrollment.domain.user.UserInfo;

public class ProfessorFixture {
    public static Professor createProfessor(Long id, UserInfo professorInfo) {
        return Professor.builder()
                .id(id)
                .userInfo(professorInfo)
                .build();
    }
}
