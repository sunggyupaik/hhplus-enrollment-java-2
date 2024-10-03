package com.hanghe.enrollment.domain.user;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserInfo {
    private String name;

    private String phone;

    private String email;

    @Builder
    public UserInfo(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
