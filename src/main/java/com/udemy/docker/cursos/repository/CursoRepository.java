package com.udemy.docker.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.udemy.docker.cursos.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	@Modifying
	@Query("DELETE FROM CursoUsuario cu WHERE cu.usuarioId=?1")
	public void eliminarUsuarioCurso(Long id);
}
