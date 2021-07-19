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

import br.com.serratecEcommerce.model.Cliente;
import br.com.serratecEcommerce.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "*")
@Api(value = "API REST Serratec E-Commerce - CLIENTE")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
	ClienteService _servicoCliente;
	
	@ApiOperation(value = "Retorna uma lista com todos os clientes")
	@GetMapping
	public List<Cliente> obterTodos(){
		return _servicoCliente.obterTodos();
	}

	@ApiOperation(value = "Retorna o cliente pelo ID")
	@GetMapping("/id/{id}")
	public Cliente obterPorId(@PathVariable(value = "id") Long id){
		return _servicoCliente.obterPorId(id);
	}
	
	@ApiOperation(value = "Retorna o cliente pelo nome")
	@GetMapping("/nome/{nome}")
	public List<Cliente> obterPorNome(@PathVariable(value = "nome") String nome){
		return _servicoCliente.obterPorNome(nome);
	}
	
	@ApiOperation(value = "Cadastra um novo cliente")
	@PostMapping
	public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente){
        return _servicoCliente.cadastrar(cliente);
	}
	
	@ApiOperation(value = "Atualiza os dados de cliente existente")
	@PutMapping("/id/{id}")
	 public Cliente atualizar(@PathVariable(value = "id") Long id, @RequestBody Cliente cliente) {
         return _servicoCliente.atualizar(id, cliente);
	 }

     @ApiOperation(value = "Deleta a conta de um cliente existente")
	 @DeleteMapping("/id/{id}")
	 public void deletar(@PathVariable(value = "id") Long id) {
    	 _servicoCliente.deletar(id);
	 }
}
