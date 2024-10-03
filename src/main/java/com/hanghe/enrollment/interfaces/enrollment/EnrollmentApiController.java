package com.hanghe.enrollment.interfaces.enrollment;

import com.hanghe.enrollment.application.enrollment.EnrollmentFacade;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
