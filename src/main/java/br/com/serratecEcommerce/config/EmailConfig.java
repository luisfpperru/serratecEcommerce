package br.com.serratecEcommerce.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:env/mail.properties")
public class EmailConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender mailSender() {
		
		// Classe que será utilizada para configurar o envio de e-mail.
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		//Pegando o host de dentro do config
		mailSender.setHost(env.getProperty("mail.smtp.host"));
		
		//Pegar a porta de dentro do config
		mailSender.setPort(env.getProperty("mail.smtp.port", Integer.class));
		
		//Pegar o usuario
		mailSender.setUsername(env.getProperty("mail.smtp.username"));
		
		//Pegar a senha
		mailSender.setPassword(env.getProperty("mail.smtp.password"));
		
		//Aqui crio um hashMap de propriedades que será utilzado para envio de email.
		Properties propriedades = new Properties();
		
		//Informando qual o protocolo que vamos utilizar.
		propriedades.put("mail.transport.protocol", "smtp");
		
		//Informo se vai precisar de autenticação.
		propriedades.put("mail.smtp.auth", true);
		
		//Aqui digo que vou usar tls
		propriedades.put("mail.smtp.starttls.enable", true);
		
		//Tempo aceitavel para envio do e-mail
		//Isso aqui pode variar muito...
		propriedades.put("mail.smtp.connectiontimeout", 10000);
		
		//Aqui estou passando as propriedades para o envio de email.
		mailSender.setJavaMailProperties(propriedades);
		
		return mailSender;
	}
	
}
