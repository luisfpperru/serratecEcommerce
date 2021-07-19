package br.com.serratecEcommerce.controller;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import br.com.serratecEcommerce.model.Produto;
import br.com.serratecEcommerce.model.ProdutoRequest;
import br.com.serratecEcommerce.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "*")
@Api(value = "API REST Serratec E-Commerce - PRODUTO")
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	
	@Autowired
	ProdutoService _servicoProduto;
	
	@ApiOperation(value = "Retorna uma lista com todos os produtos")
	@GetMapping
	public List<Produto> obterTodos(){
		return _servicoProduto.obterTodos();
	}

	@ApiOperation(value = "Retorna um produto pelo ID")
	@GetMapping("/id/{id}")
	public Produto obterPorId(@PathVariable(value = "id") Long id){
		return _servicoProduto.obterPorId(id);
	}
	
	@ApiOperation(value = "Retorna um produto que contenha aquele texto no nome")
	@GetMapping("/nome/{nome}")
	public List<Produto> obterPorNome(@PathVariable(value = "nome") String nome){
		return _servicoProduto.obterPorNome(nome);
	}
	
	@ApiOperation(value = "Adiciona um produto")
	@PostMapping
	public ResponseEntity<Produto> adicionar(@RequestBody ProdutoRequest produtoRequest){
        return _servicoProduto.adicionar(produtoRequest);
	}
	
	@ApiOperation(value = "Adiciona uma imagem ao produto")
	@PutMapping("/imagem/{id}")
	public ResponseEntity<Produto> adicionarImagemAoProduto(@PathVariable(value = "id") Long id,
															@RequestBody MultipartFile imagem){
        return _servicoProduto.adicionarImagemAoProduto(id, imagem);
	}
	
	@ApiOperation(value = "Atualiza um produto existente")
	@PutMapping("/id/{id}")
	 public Produto atualizar(@PathVariable(value = "id") Long id, @RequestBody ProdutoRequest produtoRequest) {
         return _servicoProduto.atualizar(id, produtoRequest);
	 }

	 @ApiOperation(value = "Deleta um produto existente")
	 @DeleteMapping("/id/{id}")
	 public void deletar(@PathVariable(value = "id") Long id) {
		_servicoProduto.deletar(id);
	 }
}
