package com.udemy.docker.cursos.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.docker.cursos.clients.UsuarioClientRest;
import com.udemy.docker.cursos.entity.Curso;
import com.udemy.docker.cursos.entity.CursoUsuario;
import com.udemy.docker.cursos.model.Usuario;
import com.udemy.docker.cursos.repository.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {
	static Logger logger = Logger.getLogger(CursoServiceImpl.class.getName());
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioClientRest usuarioClientRest;

	@Override
	@Transactional(readOnly = true)
	public List<Curso> getAllCursos() {
		logger.info("start CursoServiceImpl getAllCursos");
		
		List<Curso> resultado = cursoRepository.findAll();
		
		logger.info("end CursoServiceImpl getAllCursos");
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> getCursoPorId(Long id) {
		logger.info("start CursoServiceImpl getCursoPorId");
		
		Optional<Curso> cursoOpt = cursoRepository.findById(id); 
		
		logger.info("end CursoServiceImpl getCursoPorId");
		return cursoOpt;
	}

	@Override
	@Transactional
	public Curso saveCurso(Curso curso) {
		logger.info("start CursoServiceImpl saveCurso");
		
		Curso cursoSav = cursoRepository.save(curso); 
		
		logger.info("end CursoServiceImpl saveCurso");
		return cursoSav;
	}

	@Override
	@Transactional
	public void deleteCurso(Long id) {
		logger.info("start CursoServiceImpl deleteCurso");
		cursoRepository.deleteById(id);
		logger.info("end CursoServiceImpl deleteCurso");
		
	}
	
	@Override
	@Transactional
	public void deleteUsuarioCursoPorId(Long id) {
		logger.info("start CursoServiceImpl deleteUsuarioCursoPorId");
		cursoRepository.eliminarUsuarioCurso(id);
		logger.info("end CursoServiceImpl deleteUsuarioCursoPorId");
	}

	@Override
	@Transactional
	public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
		logger.info("start CursoServiceImpl asignarUsuario");
		
		Optional<Curso> cursoOpt = cursoRepository.findById(cursoId); 
		
		if(cursoOpt.isPresent()) {
			Usuario usuarioMsrv = usuarioClientRest.getUsuarioPorId(usuario.getId());
			Curso curso = cursoOpt.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMsrv.getId());
			
			curso.addCursoUsuario(cursoUsuario);
			cursoRepository.save(curso);
			
			logger.info("end CursoServiceImpl asignarUsuario");
			
			return Optional.of(usuarioMsrv);
		}
		
		logger.info("end CursoServiceImpl asignarUsuario");
		
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {

		logger.info("start CursoServiceImpl crearUsuario");
		
		Optional<Curso> cursoOpt = cursoRepository.findById(cursoId); 
		
		if(cursoOpt.isPresent()) {
			Usuario usuarioMsrv = usuarioClientRest.saveUsuario(usuario);
			Curso curso = cursoOpt.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMsrv.getId());
			
			curso.addCursoUsuario(cursoUsuario);
			cursoRepository.save(curso);
			
			logger.info("end CursoServiceImpl crearUsuario");
			
			return Optional.of(usuarioMsrv);
		}
		
		logger.info("end CursoServiceImpl crearUsuario");
		
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
		logger.info("start CursoServiceImpl eliminarUsuario");
		
		Optional<Curso> cursoOpt = cursoRepository.findById(cursoId); 
		
		if(cursoOpt.isPresent()) {
			Usuario usuarioMsrv = usuarioClientRest.getUsuarioPorId(usuario.getId());
			Curso curso = cursoOpt.get();
			CursoUsuario cursoUsuario = new CursoUsuario();
			cursoUsuario.setUsuarioId(usuarioMsrv.getId());
			
			curso.removeCursoUsuario(cursoUsuario);
			cursoRepository.save(curso);
			
			logger.info("end CursoServiceImpl eliminarUsuario");
			
			return Optional.of(usuarioMsrv);
		}
		
		logger.info("end CursoServiceImpl eliminarUsuario");
		
		return Optional.empty();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> getCursoPorIdConUsuarios(Long id) {
		logger.info("start CursoServiceImpl getCursoPorIdConUsuarios");
		
		Optional<Curso> cursoOpt = cursoRepository.findById(id);
		Curso curso = null;
		List<Long> idsUsuarios = null;
		List<Usuario> listaUsuarios = null;
		
		if(cursoOpt.isPresent()) {
			curso = cursoOpt.get();
			
			if(!curso.getCursoUsuarios().isEmpty()) {
				idsUsuarios = curso.getCursoUsuarios().stream().map(obj -> obj.getUsuarioId()).collect(Collectors.toList());
				listaUsuarios = usuarioClientRest.getUsuariosCurso(idsUsuarios);
				curso.setUsuarios(listaUsuarios);
				
				return Optional.of(curso);
			}
		}
		
		logger.info("end CursoServiceImpl getCursoPorIdConUsuarios");
		
		return Optional.empty();
	}

}
