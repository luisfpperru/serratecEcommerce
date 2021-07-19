package br.com.serratecEcommerce.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		//Constroi e retorna uma instancia do WebClient com as configurações que eu passei.
		return builder
				//.baseUrl("http://viacep.com.br/ws/") // URL que vamos conectar.
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Qual o tipo de retorno que aceitamos.
				.build();// Aqui é onde constroi o WebClint, função final.
	}
}
