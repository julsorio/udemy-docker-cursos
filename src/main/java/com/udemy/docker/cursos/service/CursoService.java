package com.udemy.docker.cursos.service;

import java.util.List;
import java.util.Optional;

import com.udemy.docker.cursos.entity.Curso;

public interface CursoService {

	public List<Curso> getAllCursos();
	
	public Optional<Curso> getCursoPorId(Long id);
	
	public Curso saveCurso(Curso curso);
	
	public void deleteCurso(Long id);
}
