package com.hanghe.enrollment.application.enrollment;

import com.hanghe.enrollment.domain.enrollment.EnrollmentService;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentFacade {
    private final EnrollmentService enrollmentService;

    public List<EnrollmentDto.Response> listEnrollments(Long studentId) {
        return enrollmentService.getEnrollments(studentId);
    }

    public EnrollmentDto.Response createEnrollment(Long courseId, Long studentId) {
        return enrollmentService.apply(courseId, studentId);
    }
}
