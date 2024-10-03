package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentDto.Response> getEnrollments(Long studentId);

    Enrollment apply(EnrollmentDto.applyRequest request);
}
