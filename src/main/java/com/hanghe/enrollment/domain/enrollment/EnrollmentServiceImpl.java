package com.hanghe.enrollment.domain.enrollment;

import com.hanghe.enrollment.domain.course.Course;
import com.hanghe.enrollment.domain.course.CourseReader;
import com.hanghe.enrollment.domain.enrollment.dto.EnrollmentDto;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentReader enrollmentReader;
    private final EnrollmentStore enrollmentStore;
    private final CourseReader courseReader;
    private final StudentReader studentReader;

    @Override
    public List<Enrollment> getEnrollments(Long studentId) {
        return enrollmentReader.getEnrollments(studentId);
    }

    @Override
    public Enrollment apply(EnrollmentDto.applyRequest request) {
        Student student = studentReader.getStudent(request.getStudentId());
        Course course = courseReader.getCourse(request.getCourseId());

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .student(student)
                .build();

        Enrollment createdEnrollment = enrollmentStore.store(enrollment);

        return createdEnrollment;
    }
}
