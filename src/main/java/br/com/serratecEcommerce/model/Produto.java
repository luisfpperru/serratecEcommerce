package br.com.serratecEcommerce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "generator_produto", sequenceName = "sequence_produto", initialValue = 1, allocationSize = 1)
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_produto")
	private Long id;
	
	@Column( nullable = false)
	private String nome;
	
	@Column( nullable = false)
	private String descricao;
	
	@Column( nullable = false)
	private Double preco;
	
	@Column( nullable = false)
	private Integer quantidadeEmEstoque;
	
	private Date dataDeCadastroDoProduto;
	
	private String imagem;
	
	//@ManyToMany(mappedBy = "produtos")
	//private List<Pedido> pedidos;
	
	@ManyToOne
	@JoinColumn(name = "categoriaId")
	private Categoria categoria;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Double getPreco() {
		return preco;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public Integer getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}
	
	public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}
	
	public Date getDataDeCadastroDoProduto() {
		return dataDeCadastroDoProduto;
	}
	
	public void setDataDeCadastroDoProduto(Date dataDeCadastroDoProduto) {
		this.dataDeCadastroDoProduto = dataDeCadastroDoProduto;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
