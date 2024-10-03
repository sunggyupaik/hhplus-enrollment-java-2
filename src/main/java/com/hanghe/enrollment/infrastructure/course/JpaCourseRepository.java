package com.hanghe.enrollment.infrastructure.course;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaCourseRepository
        extends CourseRepository, JpaRepository<Course, Long> {
    Optional<Course> findById(Long courseId);

    Course save(Course course);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select c, co from Course c left join c.courseOptions co where c.id = :courseId and co.id = :courseOptionId")
    Optional<Course> getByIdForPessimistLock(
            @Param("courseId") Long courseId,
            @Param("courseOptionId") Long courseOptionId
    );

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Course c where c.id = :courseId")
    Optional<Course> getByIdForPessimistLock(@Param("courseId") Long courseId);
}
