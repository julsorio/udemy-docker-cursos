package com.udemy.docker.cursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.docker.cursos.entity.Curso;
import com.udemy.docker.cursos.repository.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {
	@Autowired
	private CursoRepository cursoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Curso> getAllCursos() {
		return cursoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> getCursoPorId(Long id) {
		return cursoRepository.findById(id);
	}

	@Override
	@Transactional
	public Curso saveCurso(Curso curso) {
		return cursoRepository.save(curso);
	}

	@Override
	@Transactional
	public void deleteCurso(Long id) {
		cursoRepository.deleteById(id);
		
	}

}
