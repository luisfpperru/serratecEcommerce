package br.com.serratecEcommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratecEcommerce.model.Endereco;



public interface EnderecoRepository extends JpaRepository<Endereco,Long>{
	public Optional <Endereco> findById(Long Id);
}
