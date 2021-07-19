package br.com.serratecEcommerce.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	/*Metodo principal onde toda requisição bate antes de chegar no nosso endpoint.*/ 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Pego o token de dentro da requisição.
		String token = pegarToken(request);
		
		//Pego o id do usuário que está dentro do token.
		var id = jwtService.obterIdDoUsuario(token);
		
		//Se vier alguma coisa, eu vou autenticar ou permitir o acesso, caso contrario eu só vou filtrar.
		if(id.isPresent()) {

			// Pego o usuario dono do token pelo seu id.
			UserDetails usuario = customUserDetailsService.pegarUsuarioPorId(id.get());
			
			//Nesse ponto verificamos se o usuario esta autenticado ou não.
			//Aqui tbm poderiamos validar as permissões.
			UsernamePasswordAuthenticationToken autenticacao = 
					new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
			
			//Mandamos a autenticação pra propria requisição.
			//Se esta autenticado ou não.
			autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
			//Repassamos a autenticação para o contexto.
			SecurityContextHolder.getContext().setAuthentication(autenticacao);
		}
		
		//Metodo padrão que filtra as regras do usuario.
		filterChain.doFilter(request, response);		
	}
	
	//Metodo que sabe pegar o token dentro do atributo Authorization que vem no header da requisição
	private String pegarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		//Verifica se veio alguma coisa sem ser espaços em brancos dentro do Authorization.
		if(StringUtils.hasText(token)) {
			//Pega o token sem considerar o Bearer
			return token.substring(7);
		}
		
		return null;
	}

}