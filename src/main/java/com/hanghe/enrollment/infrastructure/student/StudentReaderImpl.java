package com.hanghe.enrollment.infrastructure.student;

import com.hanghe.enrollment.common.exception.StudentNotFoundException;
import com.hanghe.enrollment.domain.user.student.Student;
import com.hanghe.enrollment.domain.user.student.StudentReader;
import com.hanghe.enrollment.domain.user.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentReaderImpl implements StudentReader {
    private final StudentRepository studentRepository;

    @Override
    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }
}
