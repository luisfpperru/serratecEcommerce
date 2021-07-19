package br.com.serratecEcommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2) // Tipo de documentação, no caso swagger2
				.select() // Utilizado para pega ro projeto inteiro.
				.apis(RequestHandlerSelectors.any()) // Filtra as apis que desejar, nesse caso pega todos os controllers
				.paths(regex("/api.*")) // Filtra somente os controllers que iniciam com /api  
				.build() // Constroi o objeto
				.apiInfo(info()); // Adiciona informações complementares a documentação.
	}
	
	private ApiInfo info() {
		return new ApiInfo(
				"Lab Rat Eletrônicos", //Titulo da API
				"API REST do Lab Rat Eletrônicos do grupo Luis and the Rats", // Descrição da API
				"1.0", // Versão
				"Termos de Serviços", // Termos da licença, pode ser a URL
				new Contact("Luis and Rats", //Nome da pessoa de contato
						"luisandrats.com", // Perfil da pessoa.
						"labratinformatica@gmail.com"), // Email da pessoa
				"Apache License Version 2.0", // Descrição da licensa
				"https://www.apache.org/licenses/LICENSE-2.0", // URL da licença
				new ArrayList<VendorExtension>());
	}
}
