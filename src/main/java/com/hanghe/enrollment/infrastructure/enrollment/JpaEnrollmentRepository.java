package com.hanghe.enrollment.infrastructure.enrollment;

import com.hanghe.enrollment.domain.enrollment.Enrollment;
import com.hanghe.enrollment.domain.enrollment.EnrollmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaEnrollmentRepository
        extends EnrollmentRepository, JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);

    Enrollment save(Enrollment enrollment);

    List<Enrollment> findAll();

    @Query("SELECT COUNT(e.id) > 0 " +
            "FROM Enrollment e " +
            "WHERE e.student.id = :studentId " +
            "And e.course.id = :courseId " +
            "And e.courseOption.id = :courseOptionId")
    boolean exists(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId,
            @Param("courseOptionId") Long courseOptionId
    );
}
