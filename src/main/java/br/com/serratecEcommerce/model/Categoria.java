package br.com.serratecEcommerce.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
@SequenceGenerator(name = "generator_categoria", sequenceName = "sequence_categoria", initialValue = 1, allocationSize = 1)
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_categoria")
	private Long id;
	
	@Column( nullable = false)
	private String nome;
	
	@Column( nullable = false)
	private String descricao;
	
	@OneToMany(mappedBy = "categoria")
	private List<Produto> produtos;
	
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

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}
