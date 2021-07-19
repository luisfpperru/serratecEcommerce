package br.com.serratecEcommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "*")
@Api(value = "API REST Serratec E-Commerce - CATEGORIA")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaService _servicoCategoria;
	
	@ApiOperation(value = "Retorna uma lista com todas as categorias")
	@GetMapping
	public List<Categoria> obterTodos(){
		return _servicoCategoria.obterTodos();
	}

	@ApiOperation(value = "Retorna a categoria por ID")
	@GetMapping("/id/{id}")
	public Categoria obterPorId(@PathVariable(value = "id") Long id){
		return _servicoCategoria.obterPorId(id);
	}
	
	@ApiOperation(value = "Retorna a categoria por nome")
	@GetMapping("/nome/{nome}")
	public List<Categoria> obterPorNome(@PathVariable(value = "nome") String nome){
		return _servicoCategoria.obterPorNome(nome);
	}
	
	@ApiOperation(value = "Adiciona uma nova categoria")
	@PostMapping
	public ResponseEntity<Categoria> adicionar(@RequestBody Categoria categoria){
        return _servicoCategoria.adicionar(categoria);
	}
	
	@ApiOperation(value = "Atualiza uma categoria existente")
	@PutMapping("/id/{id}")
	 public Categoria atualizar(@PathVariable(value = "id") Long id, @RequestBody Categoria categoria) {
         return _servicoCategoria.atualizar(id, categoria);
	 }

	 @ApiOperation(value = "Deleta uma categoria existente")
	 @DeleteMapping("/id/{id}")
	 public void deletar(@PathVariable(value = "id") Long id) {
		 _servicoCategoria.deletar(id);
	 }
}
