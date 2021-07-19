package br.com.serratecEcommerce.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException{
	
	private static final long serialVersionUID = -794289228539302157L;

	public ResourceBadRequestException() {
		super("Recurso fora dos parâmetros da API");
	}
	
	public ResourceBadRequestException(String mensagem) {
		super(mensagem);
	}
	
}