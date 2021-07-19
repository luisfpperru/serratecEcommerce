package br.com.serratecEcommerce.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class ResourceUnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 7736161816811839239L;

	public ResourceUnauthorizedException() {
		super("Usuário não está autenticado pra usar esse recurso");
	}
	
	public ResourceUnauthorizedException(String mensagem) {
		super(mensagem);
	}	
	
}
