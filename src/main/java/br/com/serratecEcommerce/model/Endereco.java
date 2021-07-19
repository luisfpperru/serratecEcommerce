package br.com.serratecEcommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "generator_endereco", sequenceName = "sequence_endereco", initialValue = 1, allocationSize = 1)
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_endereco")
	private Long id;
	
	@Column( nullable = false)
	private String cep;
	
	@Column( nullable = false)
	private String rua;
	
	@Column( nullable = false)
	private String bairro;
	
	@Column( nullable = false)
	private String cidade;
	
	private Integer numero;
	
	private String complemento;
	
	@Column( nullable = false)
	private String estado;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public String getRua() {
		return rua;
	}
	
	public void setRua(String rua) {
		this.rua = rua;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getUf() {
		return estado;
	}
	
	public void setUf(String uf) {
		this.estado = uf;
	}	
	
	public String getLocalidade() {
		return cidade;
	}
	
	public void setLocalidade(String localidade) {
		this.cidade = localidade;
	}
	
	public String getLogradouro() {
		return rua;
	}
	
	public void setLogradouro(String logradouro) {
		this.rua = logradouro;
	}
}
