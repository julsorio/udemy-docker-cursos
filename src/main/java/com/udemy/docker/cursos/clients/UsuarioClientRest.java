package com.udemy.docker.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.docker.cursos.model.Usuario;

@FeignClient(name = "usuarios")
public interface UsuarioClientRest {
	
	@GetMapping(path = "/usuario/{id}")
	public Usuario getUsuarioPorId(@PathVariable(name = "id") Long id);
	
	@PostMapping(path = "/usuario")
	public Usuario saveUsuario(@RequestBody Usuario usuario);
	
	@GetMapping(path =  "/usuariosCurso")
	public List<Usuario> getUsuariosCurso(@RequestParam List<Long> ids);
}
