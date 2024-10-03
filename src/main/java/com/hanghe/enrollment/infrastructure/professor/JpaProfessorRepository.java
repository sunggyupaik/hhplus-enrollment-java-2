package com.hanghe.enrollment.infrastructure.professor;

import com.hanghe.enrollment.domain.user.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfessorRepository extends JpaRepository<Professor, Long> {
}
