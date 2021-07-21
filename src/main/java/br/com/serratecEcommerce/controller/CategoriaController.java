package br.com.serratecEcommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratecEcommerce.model.Categoria;
import br.com.serratecEcommerce.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaService _servicoCategoria;
	
	@GetMapping
	public List<Categoria> obterTodos(){
		return _servicoCategoria.obterTodos();
	}

	@GetMapping("/id/{id}")
	public Categoria obterPorId(@PathVariable(value = "id") Long id){
		return _servicoCategoria.obterPorId(id);
	}
	
	@GetMapping("/nome/{nome}")
	public List<Categoria> obterPorNome(@PathVariable(value = "nome") String nome){
		return _servicoCategoria.obterPorNome(nome);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> adicionar(@RequestBody Categoria categoria){
        return _servicoCategoria.adicionar(categoria);
	}
	
	@PutMapping("/id/{id}")
	 public Categoria atualizar(@PathVariable(value = "id") Long id, @RequestBody Categoria categoria) {
         return _servicoCategoria.atualizar(id, categoria);
	 }

	 @DeleteMapping("/id/{id}")
	 public void deletar(@PathVariable(value = "id") Long id) {
		 _servicoCategoria.deletar(id);
	 }
}
