package br.com.serratecEcommerce.model.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Mailler {

	@Autowired
	private JavaMailSender javaMailSender;
	
	/*
	 * Esse metodo é o cara que sabe pegar nossa classe e enviar e-mail com base nela.
	 * */
	public void enviar(MensagemEmail mensagem) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setFrom(mensagem.getRemetente());
		email.setSubject(mensagem.getAssunto());
		email.setText(mensagem.getCorpo());
		
		//Converte o List<String> em Array de String.
		email.setTo(mensagem.getDestinatarios()
				.toArray(new String[mensagem.getDestinatarios().size()]));
		
		//Aqui eu realmente envio
		javaMailSender.send(email);
	}
	
	public void enviarHtml(MensagemEmail mensagem) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper email = new MimeMessageHelper(message, true);
			email.setFrom(mensagem.getRemetente());
			email.setSubject(mensagem.getAssunto());
			email.setText(mensagem.getCorpo(),true);
			
			//Converte o List<String> em Array de String.
			email.setTo(mensagem.getDestinatarios()
					.toArray(new String[mensagem.getDestinatarios().size()]));
			
			//Aqui eu realmente envio
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
