package br.com.serratecEcommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.serratecEcommerce.model.Categoria;
import br.com.serratecEcommerce.model.exception.ResourceNotFoundException;
import br.com.serratecEcommerce.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository _repositorioCategoria;
	
	public List<Categoria> obterTodos(){
		return this._repositorioCategoria.findAll();
		}
	
	public Categoria obterPorId(Long id){
		return _repositorioCategoria.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Categoria não encontrada pelo ID:" + id));
	}
	
	public List<Categoria> obterPorNome(String nome){
		List<Categoria> categorias = _repositorioCategoria.findByNomeContaining(nome);
		if (categorias.isEmpty())
			throw new ResourceNotFoundException("Categoria não encontrada pelo nome:" + nome);
		return categorias;
	}
	
	public ResponseEntity<Categoria> adicionar(Categoria categoria) {
		categoria.setId(null);
		var adicionado = _repositorioCategoria.save(categoria);
		return new ResponseEntity<>(adicionado, HttpStatus.CREATED);
	}
	
	 public Categoria atualizar(Long id,Categoria categoria) {
 		_repositorioCategoria.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Categoria não encontrada pelo ID:" + id));
 		 categoria.setId(id);
         return this._repositorioCategoria.save(categoria);
	 }

	 public void deletar(Long id) {
		 _repositorioCategoria.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Categoria não encontrada pelo ID:" + id));
         this._repositorioCategoria.deleteById(id);
	 }
}

