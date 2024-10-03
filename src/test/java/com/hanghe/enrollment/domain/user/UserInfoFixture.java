package com.hanghe.enrollment.domain.user;

public class UserInfoFixture {
    public static UserInfo createUserInfo(String name, String email, String phone) {
        return UserInfo.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .build();
    }
}
