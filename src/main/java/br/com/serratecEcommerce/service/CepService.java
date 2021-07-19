package br.com.serratecEcommerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.serratecEcommerce.model.Endereco;
import br.com.serratecEcommerce.model.exception.ResourceNotFoundException;
import reactor.core.publisher.Mono;

@Service
public class CepService {

	@Autowired
	private WebClient cepWebClient;
	
	public Endereco obterEnderecoPorCep(String cep) {
			
			// Isso aqui acontece de forma asyncrona, de form async
			Mono<Endereco> monoEndereco = this.cepWebClient
				.method(HttpMethod.GET) // Metodo que vou usar na requisição.
				.uri("http://viacep.com.br/ws/{cep}/json", cep) // Aqui colocamos a variavel dentro da rota.
				.retrieve() // Esse é o metodo que realmente dispara a requisição.
				.bodyToMono(Endereco.class); // Pega o retorno da requisição e transforma automagicamente em um Endereco.
					
			// Aqui eu poderia tbm me comunicar com outras apis simultaneamente.
			
			var endereco = monoEndereco.block(); // Fica aguardando o retorno da api e devolve o body obtido.
			if (endereco.equals(null))
				throw new ResourceNotFoundException("O CEP não foi encontrado!");
			return endereco;
	}
}
