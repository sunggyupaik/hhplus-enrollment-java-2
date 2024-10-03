package com.hanghe.enrollment.infrastructure.course.option;

import com.hanghe.enrollment.domain.course.option.CourseDate;
import com.hanghe.enrollment.domain.course.option.CourseOption;
import com.hanghe.enrollment.domain.course.option.CourseOptionRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaCourseOptionRepository
        extends CourseOptionRepository, JpaRepository<CourseOption, Long> {
    List<CourseOption> findAllByCourseDate(CourseDate courseDate);

    Optional<CourseOption> findById(Long courseOptionId);

    CourseOption saveAndFlush(CourseOption courseOption);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select co from CourseOption co where co.id = :courseOptionId and co.course.id = :courseId")
    Optional<CourseOption> findByIdForPessimistLock(
            @Param("courseId") Long courseId,
            @Param("courseOptionId") Long courseOptionId
    );
}
