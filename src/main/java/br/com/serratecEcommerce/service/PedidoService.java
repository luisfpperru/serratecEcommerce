package br.com.serratecEcommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.serratecEcommerce.model.Cliente;
import br.com.serratecEcommerce.model.Pedido;
import br.com.serratecEcommerce.model.PedidoRequest;
import br.com.serratecEcommerce.model.Produto;
import br.com.serratecEcommerce.model.email.MensagemEmail;
import br.com.serratecEcommerce.model.exception.ResourceBadRequestException;
import br.com.serratecEcommerce.model.exception.ResourceNotFoundException;
import br.com.serratecEcommerce.repository.ClienteRepository;
import br.com.serratecEcommerce.repository.PedidoRepository;
import br.com.serratecEcommerce.repository.ProdutoRepository;
import br.com.serratecEcommerce.util.FormataData;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository _repositorioPedido;
	
	@Autowired
	private ProdutoRepository _repositorioProduto;
	
	@Autowired
	private ClienteRepository _repositorioCliente;
	
	@Autowired 
	private EmailService _serviceEmail;
	
	public List<Pedido> obterTodos(){
		return this._repositorioPedido.findAll();
	}
	
	public Pedido obterPorId(Long id){
		return this._repositorioPedido.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Cliente não encontrado(a) pelo ID:" + id));
	}
	
	public ResponseEntity<Pedido> adicionar(PedidoRequest pedidoRequest){
		var pedido = new Pedido();
		if (!pedidoRequest.getProdutosId().isEmpty()) {
		pedidoRequest.getProdutosId().forEach(produtoId -> 
			{Optional<Produto> produto = _repositorioProduto.findById(produtoId);
			if (produto.isPresent())
				pedido.getProdutos().add(produto.get());	
		});
		}
		Long clienteId = pedidoRequest.getClienteId();
		if (clienteId != null) {
			var cliente = _repositorioCliente.findById(clienteId).orElseThrow( ()-> new ResourceNotFoundException("Cliente não encontrado(a) pelo ID:" + clienteId));
 			pedido.setCliente(cliente);
		}
		pedido.setNumeroDoPedido( (int) (Math.random()*((999999- 100000) + 1)) + 100000);
		pedido.setStatus(pedidoRequest.getStatus());
		calcularValorTotal(pedido);
		pedido.setDataDoPedido(new Date());
		var adicionado = this._repositorioPedido.save(pedido);
		checarPedidoFinalizado(adicionado);
        return new ResponseEntity<>(adicionado, HttpStatus.CREATED);
	}
	
	 public Pedido atualizar(Long id, PedidoRequest pedidoRequest) {
 		Pedido pedido = _repositorioPedido.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Pedido não encontrado(a) pelo ID:" + id));	
 		if (!pedidoRequest.getProdutosId().isEmpty()) {
 		pedidoRequest.getProdutosId().forEach(produtoId -> 
 			{Optional<Produto> produto = _repositorioProduto.findById(produtoId);
			if (produto.isPresent())
				pedido.getProdutos().add(produto.get());	
		});
 		}
		Long clienteId = pedidoRequest.getClienteId();
		if (clienteId != null) {
				Cliente cliente = _repositorioCliente.findById(pedidoRequest.getClienteId()).orElseThrow( ()-> new ResourceNotFoundException("Cliente não encontrado(a) pelo ID:" + id));
				pedido.setCliente(cliente);
		}
 		validarPedido(pedido);
		pedido.setStatus(pedidoRequest.getStatus());
		calcularValorTotal(pedido);
 		pedido.setId(id); 		
 		var atualizado = this._repositorioPedido.save(pedido);
 		checarPedidoFinalizado(pedido);
 		return atualizado;
	 }

	 public void deletar(Long id) {
			_repositorioPedido.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Pedido não encontrado(a) pelo ID:" + id));
         this._repositorioPedido.deleteById(id);
	 }
	 
	 private void calcularValorTotal(Pedido pedido) {
		 var valorTotal = 0.0;
		 if (!pedido.getProdutos().isEmpty()) {
			 for (Produto produto:pedido.getProdutos())
			 	valorTotal += produto.getPreco();					
		 }
		 pedido.setValorTotalDoPedido(valorTotal);
	 }
	private void validarPedido(Pedido pedido) {
		if (pedido.getStatus().equals("finalizado") || pedido.getStatus().equals("Finalizado")) {
 			throw new ResourceBadRequestException("O usuário está tentando modificar um pedido já finalizado.");
		 }
	}
	private void checarPedidoFinalizado(Pedido pedido) {
		if (pedido.getStatus().equals("finalizado") || pedido.getStatus().equals("Finalizado")) {
			var destinatarios = new ArrayList<String>();
			destinatarios.add("labratinformatica@gmail.com");
			destinatarios.add(pedido.getCliente().getEmail());
			String html = "<html>"
					+ "<head>"
					+ "<title>Lab Rat Eletronicos</title>"
					+ "</head>"
					+ "<body style=\"text-align: center; font-family: Verdana, Geneva, Tahoma, sans-serif\" > "
					+ "<header style=\"background-color: #062035; color: white\"> "
					+ "<img src=\'https://i.ibb.co/ccyhrhC/logo.png\' alt=\"\" /><div>"
					+ "<h1>Prezado(a) "+pedido.getCliente().getNome()+"</h1>"
							+ "<h2>Sua compra foi efetuada com sucesso!</h2>"
							+ " <br></div>"
							+ "<div style=\"color:#062035; background-color: white;\">"
							+ "Detalhes da compra: Nº"+pedido.getNumeroDoPedido()+"</div>"+exibirProdutosNoPedido(pedido)+""
									+ "<div style=\"color:white; background-color: #062035;\">"
									+ "<strong>TOTAL DA COMPRA: R$"+pedido.getValorTotalDoPedido()+"</strong></div>"
											+ "<div style=\"color:white; background-color: #062035;\">"
											+ "Date de entrega prevista: "+ calculaDataDeEntrega()+"</div>"
											+ "</body>"
											+ "</html>";

			var email  = new MensagemEmail("Sua compra foi finalizada com sucesso!",
											html,	   
										   "Lab Rat Eletronicos <labratinformatica@gmail.com>",
										   destinatarios);
			_serviceEmail.enviarHtml(email);
		}
	}
	 private String calculaDataDeEntrega() {
		 return FormataData.formatarDataPadraoBrasil(new Date(new Date().getTime() + 5*24*3600*1000)); // 5 dias * 24 horas * 3600 segundos * 1000 milisegundos
	 }
	 private String exibirProdutosNoPedido(Pedido pedido) {
		String lista = "";
		if (!pedido.getProdutos().isEmpty()) {
			for( Produto produto: pedido.getProdutos())
        		lista += String.format("<br>  %s:  R$%s - %s",produto.getNome(),produto.getPreco(),produto.getDescricao());
		} 
		return lista;
	 }
}
