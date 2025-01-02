package com.udemy.docker.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.udemy.docker.cursos.model.Usuario;

@FeignClient(name = "usuarios", url = "localhost:8001/usuarios/api/v1/")
public interface UsuarioClientRest {
	
	@GetMapping(path = "/usuario/{id}")
	public Usuario getUsuarioPorId(@PathVariable(name = "id") Long id);
	
	@PostMapping(path = "/usuario")
	public Usuario saveUsuario(@RequestBody Usuario usuario);
}
