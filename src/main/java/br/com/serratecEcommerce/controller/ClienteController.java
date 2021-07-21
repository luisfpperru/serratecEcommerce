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

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
	ClienteService _servicoCliente;
	
	@GetMapping
	public List<Cliente> obterTodos(){
		return _servicoCliente.obterTodos();
	}

	@GetMapping("/id/{id}")
	public Cliente obterPorId(@PathVariable(value = "id") Long id){
		return _servicoCliente.obterPorId(id);
	}
	
	@GetMapping("/nome/{nome}")
	public List<Cliente> obterPorNome(@PathVariable(value = "nome") String nome){
		return _servicoCliente.obterPorNome(nome);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente){
        return _servicoCliente.cadastrar(cliente);
	}
	
	@PutMapping("/id/{id}")
	 public Cliente atualizar(@PathVariable(value = "id") Long id, @RequestBody Cliente cliente) {
         return _servicoCliente.atualizar(id, cliente);
	 }

	 @DeleteMapping("/id/{id}")
	 public void deletar(@PathVariable(value = "id") Long id) {
    	 _servicoCliente.deletar(id);
	 }
}
