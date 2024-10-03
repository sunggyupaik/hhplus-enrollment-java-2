package com.hanghe.enrollment.domain.user.student;

import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(Long id);
}
