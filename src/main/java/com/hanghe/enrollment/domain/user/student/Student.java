package com.hanghe.enrollment.domain.user.student;

import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.user.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Student {
    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    @Embedded
    private UserInfo userInfo;

    @OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Enrollment> enrollments = new ArrayList<>();

    @Builder
    public Student(Long id, UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }
}
