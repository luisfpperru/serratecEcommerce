package br.com.serratecEcommerce.dto;

import br.com.serratecEcommerce.model.Cliente;

public class LoginResponse {
	
	private String token;
	
	private Cliente cliente;
		
	public LoginResponse(String token, Cliente cliente) {
		this.token = token;
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
		
}
