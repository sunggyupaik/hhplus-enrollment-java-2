package com.hanghe.enrollment.infrastructure.student;

import com.hanghe.enrollment.common.exception.StudentNotFoundException;
import com.hanghe.enrollment.domain.user.student.StudentReader;
import com.hanghe.enrollment.domain.user.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StudentReaderImplTest {
    private StudentReader studentReader;
    private StudentRepository studentRepository;

    private static final Long NOT_EXISTED_STUDENT_ID = 999L;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        studentReader = new StudentReaderImpl(studentRepository);
    }

    @Test
    @DisplayName("주어진 식별자에 해당하는 신청자가 없으면 찾을 수 없다는 예외를 반환한다.")
    void getStudentWithNotExistedId_throwsNotFoundException() {
        given(studentRepository.findById(NOT_EXISTED_STUDENT_ID)).willReturn(Optional.empty());

        assertThatThrownBy(
                () -> studentReader.getStudent(NOT_EXISTED_STUDENT_ID)
        )
                .isInstanceOf(StudentNotFoundException.class);
    }
}