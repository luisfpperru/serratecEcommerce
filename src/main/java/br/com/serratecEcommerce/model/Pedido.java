package br.com.serratecEcommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
@SequenceGenerator(name = "generator_pedido", sequenceName = "sequence_pedido", initialValue = 1, allocationSize = 1)
@SequenceGenerator(name = "generator_nro_pedido", sequenceName = "sequence_pedido", initialValue = 1, allocationSize = 1)
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_pedido")
	private Long id;
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_nro_pedido")
	private Integer numeroDoPedido;
		
	private Double valorTotalDoPedido;
	
	private Date dataDoPedido;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "clienteId")
	private Cliente cliente;
	
	@ManyToMany
	@JoinTable(
		name = "produtos_pedidos", 
		joinColumns = @JoinColumn(name="pedidoId"), 
		inverseJoinColumns = @JoinColumn(name = "produtoId"))
	private List<Produto> produtos = new ArrayList<Produto>();
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getNumeroDoPedido() {
		return numeroDoPedido;
	}
	
	public void setNumeroDoPedido(Integer numeroDoPedido) {
		this.numeroDoPedido = numeroDoPedido;
	}
	
	public Double getValorTotalDoPedido() {
		return valorTotalDoPedido;
	}
	
	public void setValorTotalDoPedido(Double valorTotalDoPedido) {
		this.valorTotalDoPedido = valorTotalDoPedido;
	}
	
	public Date getDataDoPedido() {
		return dataDoPedido;
	}
	
	public void setDataDoPedido(Date dataDoPedido) {
		this.dataDoPedido = dataDoPedido;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
