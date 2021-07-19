package br.com.serratecEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratecEcommerce.dto.LoginRequest;
import br.com.serratecEcommerce.dto.LoginResponse;
import br.com.serratecEcommerce.service.ClienteService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private ClienteService servicoCliente;
		
	@PostMapping
	public LoginResponse login(@RequestBody LoginRequest request) {	
		return servicoCliente.logar(request.getEmail(), request.getSenha());
	}
		
}
