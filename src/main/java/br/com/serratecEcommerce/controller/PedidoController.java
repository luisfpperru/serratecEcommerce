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

import br.com.serratecEcommerce.model.Pedido;
import br.com.serratecEcommerce.model.PedidoRequest;
import br.com.serratecEcommerce.model.Produto;
import br.com.serratecEcommerce.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "*")
@Api(value = "API REST Serratec E-Commerce - PEDIDOS")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	@Autowired
	PedidoService _servicoPedido;
	
	@ApiOperation(value = "Retorna uma lista com todos os pedidos")
	@GetMapping
	public List<Pedido> obterTodos(){
		return _servicoPedido.obterTodos();
	}

	@ApiOperation(value = "Retorna um pedido pelo ID")
	@GetMapping("/id/{id}")
	public Pedido obterPorId(@PathVariable(value = "id") Long id){
		return _servicoPedido.obterPorId(id);
	}
	
	@ApiOperation(value = "Adiciona um pedido")
	@PostMapping
	public ResponseEntity<Pedido>  adicionar(@RequestBody PedidoRequest pedido){
        return _servicoPedido.adicionar(pedido);
	}
	
	@ApiOperation(value = "Atualiza um pedido existente")
	@PutMapping("/id/{id}")
	 public Pedido atualizar(@PathVariable(value = "id") Long id, @RequestBody PedidoRequest pedidos) {
         return _servicoPedido.atualizar(id,pedidos);
	 }
	
	 @ApiOperation(value = "Deleta um pedido existente")
	 @DeleteMapping("/id/{id}")
	 public void deletar(@PathVariable(value = "id") Long id) {
		_servicoPedido.deletar(id);
	 }
}
