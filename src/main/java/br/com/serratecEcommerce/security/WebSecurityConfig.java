package br.com.serratecEcommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Aqui informo que é uma classe de segurança do WebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	
	//Metodo padrão simplesmente configura nosso custom com nosso metodo de codificar senhas.
	@Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEnconder());
    }
	
	//Metodo que devolve a instância do objeto que sabe devolver o nosso padrão de codificação.
	//Isso não tem nada a ver com token JWT.
	//Aqui será usado para codificar a senha do usuario gerando um hash.
	@Bean
	public PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//Metodo que tem a configuração global de acessos e permissões por rotas.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Parte padrão para ignorar por enquanto.
		http
			.cors().and().csrf().disable()
			.exceptionHandling()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			
			/*
			 * Daqui pra baixo é aonde eu vou futucar e fazer as minhas validações.
			 * Aqui estamos informando as rotas não vão precisar de autenticação.
			 * 
			 */
			
			.antMatchers("/api/**") // Esse cara aqui pode receber vários
			.permitAll() // Informa que todos podem acessar
			
			/*	
			.antMatchers("/api/login") // Esse cara aqui pode receber vários
			.permitAll() // Informa que todos podem acessar
			
			.antMatchers("/swagger-ui.html","/swagger-ui.html/**") // Esse cara aqui pode receber vários
			.permitAll() // Informa que todos podem acessar
			
			.antMatchers(HttpMethod.POST,"/api/clientes")
			.permitAll()
			
			.antMatchers(HttpMethod.GET,"/api/pedidos")
			.permitAll()
			
			.antMatchers(HttpMethod.DELETE,"/api/pedidos")
			.permitAll()
			
			.antMatchers("/api/categorias","/api/categorias/**")
			.permitAll()
			
			.antMatchers("/api/produtos","/api/produtos/**")
			.permitAll()
			*/
			
			.anyRequest().authenticated(); //Digo que as demais requisições devem ser autenticadas.
			
		//Aqui eu informo que antes de qualquer requisição o http deve usar nosso jetAutenticationFilter.
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
			
	}	
	
	//Metodo padrão: Esse metodo é obrigatório para conseguimos trabalhar com a autenticação no nosso login.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
