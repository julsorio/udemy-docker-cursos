package com.udemy.docker.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.docker.cursos.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
