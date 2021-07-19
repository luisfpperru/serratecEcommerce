package br.com.serratecEcommerce.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

	@Value("${caminho.raiz}")
	private String raiz;
	
	public String salvar(String caminho, MultipartFile arquivo) {
		
		Path diretorio = Paths.get(this.raiz, caminho);
		Path arquivoPath = diretorio.resolve(arquivo.getOriginalFilename());
		
		try {
			
			Files.createDirectories(diretorio);
			arquivo.transferTo(arquivoPath.toFile());
			//Aqui salvar o caminho no banco...
			return arquivoPath.toString();
			
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar salvar o arquivo!!!");
		}
		
	}
}
