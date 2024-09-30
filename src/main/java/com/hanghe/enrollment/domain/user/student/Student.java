package com.hanghe.enrollment.domain.user.student;

import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.user.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    @Embedded
    private UserInfo userInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Enrollment> enrollments = new ArrayList<>();
}
