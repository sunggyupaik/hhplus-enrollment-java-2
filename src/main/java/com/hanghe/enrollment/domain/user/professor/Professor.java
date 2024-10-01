package com.hanghe.enrollment.domain.user.professor;

import com.hanghe.enrollment.domain.user.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor {
    @Id
    @GeneratedValue
    @Column(name = "professor_id")
    private Long id;

    @Embedded
    private UserInfo userInfo;

    @Builder
    public Professor(Long id, UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }
}
