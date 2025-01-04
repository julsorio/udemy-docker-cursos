package com.udemy.docker.cursos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.docker.cursos.entity.Curso;
import com.udemy.docker.cursos.model.Usuario;
import com.udemy.docker.cursos.service.CursoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1")
public class CursoController {
	static Logger logger = Logger.getLogger(CursoController.class.getName());
	
	@Autowired
	private CursoService cursoService;

	@GetMapping(path = "/cursos")
	public ResponseEntity<List<Curso>> getAllCursos() {
		logger.info("start CursoController getAllCursos");
		
		List<Curso> listaCursos = cursoService.getAllCursos();
		ResponseEntity<List<Curso>> respuesta = new ResponseEntity<>(listaCursos, HttpStatus.OK);
		
		logger.info("end CursoController getAllCursos");
		
		return respuesta;
	}
	
	@GetMapping(path = "/curso/{id}")
	public ResponseEntity<Curso> getCursoPorId(@PathVariable Long id) {
		logger.info("start CursoController getCursoPorId");
		
		//Optional<Curso> curso = cursoService.getCursoPorId(id);
		Optional<Curso> curso = cursoService.getCursoPorIdConUsuarios(id);
		ResponseEntity<Curso> respuesta = null;
		
		
		if(curso.isEmpty()) {
			respuesta = new ResponseEntity<Curso>(HttpStatus.NOT_FOUND);
		} else {
			respuesta = new ResponseEntity<Curso>(curso.get(), HttpStatus.OK);
		}
		
		logger.info("end CursoController getCursoPorId");
		
		return respuesta;
	}
	
	@PostMapping(path = "/curso")
	public ResponseEntity<?> saveCurso(@Valid @RequestBody Curso curso, BindingResult bindingResult) {
		logger.info("start CursoController saveCurso");
		
		ResponseEntity<?> respuesta = null;
		
		if(bindingResult.hasErrors()) {
			return validar(bindingResult);
		}
		
		Curso cursoGuardado = null;
		
		try {
			cursoGuardado = cursoService.saveCurso(curso);
			respuesta = new ResponseEntity<Curso>(cursoGuardado, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("error save curso " + e.getMessage());
			respuesta = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		logger.info("end CursoController saveCurso");
		
		return respuesta;
	}
	
	@PutMapping(path = "/curso/{id}")
	public ResponseEntity<?> updateCurso(@PathVariable Long id, @Valid @RequestBody Curso curso, BindingResult bindingResult) {
		logger.info("start CursoController updateCurso");
		
		ResponseEntity<?> respuesta = null;
		
		if(bindingResult.hasErrors()) {
			return validar(bindingResult);
		}
		
		Optional<Curso> cursoGuardado = cursoService.getCursoPorId(id);
		
		if(cursoGuardado.isEmpty()) {
			respuesta = new ResponseEntity<Curso>(HttpStatus.NOT_FOUND);
		} else {
			respuesta = new ResponseEntity<Curso>(cursoService.saveCurso(curso), HttpStatus.CREATED);
		}
		
		logger.info("end CursoController updateCurso");
		
		return respuesta;
	}
	
	@DeleteMapping(path = "/curso/{id}")
	public ResponseEntity<?> deleteCurso(@PathVariable Long id) {
		logger.info("start CursoController deleteCurso");
		
		ResponseEntity<?> respuesta = null;
		
		Optional<Curso> cursoGuardado = cursoService.getCursoPorId(id);
		
		if(cursoGuardado.isEmpty()) {
			respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			cursoService.deleteCurso(id);
			respuesta = new ResponseEntity<>(HttpStatus.OK);
		}
		
		logger.info("end CursoController deleteCurso");
		
		return respuesta;
	}
	
	@PutMapping(path = "/addUsuario/{cursoId}")
	public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		logger.info("start CursoController asignarUsuario");
		ResponseEntity<?> respuesta = null;
		Optional<Usuario> usuarioOpt = null;
		
		try {
			usuarioOpt = cursoService.asignarUsuario(usuario, cursoId);
			
			if(usuarioOpt.isPresent()) {
				respuesta = new ResponseEntity<>(usuarioOpt.get(), HttpStatus.CREATED);
			} else {
				respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			respuesta = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		logger.info("end CursoController asignarUsuario");
		
		return respuesta; 
	}
	
	@PostMapping(path = "/createUsuario/{cursoId}")
	public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		logger.info("start CursoController crearUsuario");
		ResponseEntity<?> respuesta = null;
		Optional<Usuario> usuarioOpt = null;
		
		try {
			usuarioOpt = cursoService.crearUsuario(usuario, cursoId);
			
			if(usuarioOpt.isPresent()) {
				respuesta = new ResponseEntity<>(usuarioOpt.get(), HttpStatus.CREATED);
			} else {
				respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			respuesta = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		logger.info("end CursoController crearUsuario");
		
		return respuesta; 
	}
	
	@DeleteMapping(path = "/deleteUsuario/{cursoId}")
	public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
		logger.info("start CursoController eliminarUsuario");
		ResponseEntity<?> respuesta = null;
		Optional<Usuario> usuarioOpt = null;
		
		try {
			usuarioOpt = cursoService.eliminarUsuario(usuario, cursoId);
			
			if(usuarioOpt.isPresent()) {
				respuesta = new ResponseEntity<>(usuarioOpt.get(), HttpStatus.OK);
			} else {
				respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			respuesta = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		logger.info("end CursoController eliminarUsuario");
		
		return respuesta; 
	}
	
	@DeleteMapping(path = "/deleteUsuarioCurso/{usuarioId}")
	public ResponseEntity<?> eliminarUsuarioCurso(@PathVariable Long usuarioId) {
		logger.info("start CursoController eliminarUsuarioCurso");
		
		cursoService.deleteUsuarioCursoPorId(usuarioId);
		
		ResponseEntity<?> respuesta = new ResponseEntity<>(HttpStatus.OK);
		
		logger.info("end CursoController eliminarUsuarioCurso");
		
		return respuesta;
	}
	
	private ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
		Map<String, String> mapaErrores = new HashMap<>();
		bindingResult.getFieldErrors().forEach(error -> {
			mapaErrores.put(error.getField(),
					"Error en el campo " + error.getField() + " : " + error.getDefaultMessage());
		});

		return new ResponseEntity<Map<String, String>>(mapaErrores, HttpStatus.BAD_REQUEST);
	}
}
