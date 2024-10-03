package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.common.exception.EntityNotFoundException;
import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseRepository;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<Enrollment> getEnrollment(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    public Enrollment apply(EnrollmentDto.applyRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(request.getStudentId()));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(request.getStudentId()));

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .student(student)
                .build();

        Enrollment createdEnrollment = enrollmentRepository.save(enrollment);

        return createdEnrollment;
    }
}
