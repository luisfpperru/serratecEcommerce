package br.com.serratecEcommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratecEcommerce.model.Pedido;



public interface PedidoRepository extends JpaRepository<Pedido,Long>{
	public Optional<Pedido> findById(Long id);

}
