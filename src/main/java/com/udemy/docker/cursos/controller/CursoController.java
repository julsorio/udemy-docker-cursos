package com.udemy.docker.cursos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.udemy.docker.cursos.service.CursoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;

	@GetMapping(path = "/cursos")
	public ResponseEntity<List<Curso>> getAllCursos() {
		return new ResponseEntity<List<Curso>>(cursoService.getAllCursos(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/curso/{id}")
	public ResponseEntity<Curso> getCursoPorId(@PathVariable Long id) {
		Optional<Curso> curso = cursoService.getCursoPorId(id);
		
		if(curso.isEmpty()) {
			return new ResponseEntity<Curso>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Curso>(curso.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping(path = "/curso")
	public ResponseEntity<?> saveCurso(@Valid @RequestBody Curso curso, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return validar(bindingResult);
		}
		
		Curso cursoGuardado = null;
		
		try {
			cursoGuardado = cursoService.saveCurso(curso);
			return new ResponseEntity<Curso>(cursoGuardado, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("error save curso " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/curso/{id}")
	public ResponseEntity<?> updateCurso(@PathVariable Long id, @Valid @RequestBody Curso curso, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return validar(bindingResult);
		}
		
		Optional<Curso> cursoGuardado = cursoService.getCursoPorId(id);
		
		if(cursoGuardado.isEmpty()) {
			return new ResponseEntity<Curso>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Curso>(cursoService.saveCurso(curso), HttpStatus.CREATED);
		}
	}
	
	@DeleteMapping(path = "/curso/{id}")
	public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
		Optional<Curso> cursoGuardado = cursoService.getCursoPorId(id);
		
		if(cursoGuardado.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			cursoService.deleteCurso(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
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
