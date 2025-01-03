package com.udemy.docker.cursos.service;

import java.util.List;
import java.util.Optional;

import com.udemy.docker.cursos.entity.Curso;
import com.udemy.docker.cursos.model.Usuario;

public interface CursoService {

	public List<Curso> getAllCursos();
	
	public Optional<Curso> getCursoPorId(Long id);
	
	public Optional<Curso> getCursoPorIdConUsuarios(Long id);
	
	public Curso saveCurso(Curso curso);
	
	public void deleteCurso(Long id);
	
	public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
	
	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
	
	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
	
}
