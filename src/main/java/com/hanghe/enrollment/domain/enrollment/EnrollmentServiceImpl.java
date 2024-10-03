package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseReader;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentReader enrollmentReader;
    private final EnrollmentStore enrollmentStore;
    private final CourseReader courseReader;
    private final StudentReader studentReader;

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentDto.Response> getEnrollments(Long studentId) {
        List<Enrollment> enrollments = enrollmentReader.getEnrollments(studentId);

        return enrollments.stream()
                .map(EnrollmentDto.Response::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Enrollment apply(EnrollmentDto.applyRequest request) {
        Student student = studentReader.getStudent(request.getStudentId());
        Course course = courseReader.getCourse(request.getCourseId());

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .student(student)
                .build();

        return enrollmentStore.store(enrollment);
    }
}
