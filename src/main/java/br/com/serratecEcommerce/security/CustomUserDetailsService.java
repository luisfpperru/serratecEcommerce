package br.com.serratecEcommerce.security;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.serratecEcommerce.model.Cliente;
import br.com.serratecEcommerce.repository.ClienteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private ClienteRepository repositorioCliente;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		Cliente cliente = getCliente(() -> repositorioCliente.findByEmail(email));
		return cliente;
	}
	
	public UserDetails pegarUsuarioPorId(Long id) {
		Cliente cliente = getCliente(() -> repositorioCliente.findById(id));
		return cliente;
	}
	
	
	private Cliente getCliente(Supplier<Optional<Cliente>> supplier) {
		return supplier.get().orElseThrow(() -> 
				new UsernameNotFoundException("Cliente não encontrado"));
	}

}
