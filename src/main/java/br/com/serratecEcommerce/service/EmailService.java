package br.com.serratecEcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratecEcommerce.model.email.Mailler;
import br.com.serratecEcommerce.model.email.MensagemEmail;

@Service
public class EmailService {
	
		@Autowired
		Mailler mailler;
		
		public String enviarEmail(MensagemEmail email) {
			
			try {
				mailler.enviar(email);
				return "Email enviado com sucesso!";
				
			} catch (Exception e) {
				return "Algo deu errado no envio...";
			}
		}
		
		public String enviarHtml(MensagemEmail email) {
			
			try {
				mailler.enviarHtml(email);
				return "Email enviado com sucesso!";
				
			} catch (Exception e) {
				return "Algo deu errado no envio...";
			}
		}
}
