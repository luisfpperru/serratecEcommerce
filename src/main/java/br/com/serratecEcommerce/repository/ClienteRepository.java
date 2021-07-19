package br.com.serratecEcommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratecEcommerce.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long>{
	public Optional<Cliente> findById(Long id);
	public List<Cliente> findByNome(String nome);
	public Optional<Cliente> findByEmail(String email);
}
