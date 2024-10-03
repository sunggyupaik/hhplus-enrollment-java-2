package com.hanghe.enrollment.interfaces.enrollment;

import com.hanghe.enrollment.application.enrollment.EnrollmentFacade;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollment")
public class EnrollmentApiController {
    private final EnrollmentFacade enrollmentFacade;

    @GetMapping("/{id}")
    public List<EnrollmentDto.Response> listEnrollments(@PathVariable("id") Long studentId) {
        return enrollmentFacade.listEnrollments(studentId);
    }

    @PostMapping("/apply/course/{courseId}/student/{studentId}")
    public EnrollmentDto.Response createEnrollment(@PathVariable Long courseId, @PathVariable Long studentId) {
        return enrollmentFacade.createEnrollment(courseId, studentId);
    }
}
