package com.hanghe.enrollment.infrastructure.professor;

import com.hanghe.enrollment.domain.user.professor.Professor;
import com.hanghe.enrollment.domain.user.professor.ProfessorRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfessorRepository
        extends ProfessorRepository, JpaRepository<Professor, Long> {
    Professor save(Professor professor);
}
