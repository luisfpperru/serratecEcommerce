package br.com.serratecEcommerce.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoRequest {
	
	private String status;

	private Long clienteId;

	private List<Long> produtosId = new ArrayList<Long>();

	public List<Long> getProdutosId() {
		return produtosId;
	}

	public void setProdutosId(List<Long> produtosId) {
		this.produtosId = produtosId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
}
