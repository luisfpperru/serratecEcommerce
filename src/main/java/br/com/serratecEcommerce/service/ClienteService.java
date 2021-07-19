package br.com.serratecEcommerce.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.serratecEcommerce.dto.LoginResponse;
import br.com.serratecEcommerce.model.Cliente;
import br.com.serratecEcommerce.model.Endereco;
import br.com.serratecEcommerce.model.email.MensagemEmail;
import br.com.serratecEcommerce.model.exception.ResourceBadRequestException;
import br.com.serratecEcommerce.model.exception.ResourceNotFoundException;
import br.com.serratecEcommerce.repository.ClienteRepository;
import br.com.serratecEcommerce.repository.EnderecoRepository;
import br.com.serratecEcommerce.security.JWTService;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository _repositorioCliente;
	
	@Autowired
	private EnderecoRepository _repositorioEndereco;
	
	@Autowired
	private CepService servicoCep;
	
	@Autowired 
	private EmailService _serviceEmail;
	
	private static final String headerPrefix = "Bearer ";
	
	@Autowired
	private JWTService jwtService;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;	
	
	public List<Cliente> obterTodos(){
		return this._repositorioCliente.findAll();
	}
	
	public Cliente obterPorId(Long id){
		return this._repositorioCliente.findById(id)
									   .orElseThrow( ()-> new ResourceNotFoundException("Cliente não encontrado(a) pelo ID:" + id));
		
	}
	
	public List<Cliente> obterPorNome(String nome){
		var clientes = this._repositorioCliente.findByNome(nome);
		if (clientes.isEmpty())
			throw new ResourceNotFoundException("Cliente não encontrado(a) pelo Nome:" + nome);
		return clientes;
	}
	
	public ResponseEntity<Cliente> cadastrar(Cliente cliente) {
		if (cliente.getCpfOuCnpj() != null)
			validaCpf(cliente.getCpfOuCnpj());
		if (cliente.getEndereco() != null) {
			autocompletarEndereco(cliente.getEndereco());
			validarEndereco(cliente.getEndereco());
		}
		cliente.setId(null);
		
		if (_repositorioCliente.findByEmail(cliente.getEmail()).isPresent()) {
			throw new ResourceBadRequestException("Já existe um cliente com esse email!");
			//Aqui lança uma exception informando que ja existe um usuario com este e-mail.
		}
		String senha = passwordEncoder.encode(cliente.getSenha());
		cliente.setSenha(senha);
		_repositorioCliente.save(cliente);
		enviarEmailCadastro(cliente);
		return new ResponseEntity<>(cliente, HttpStatus.CREATED);
	}
	
	 public Cliente atualizar(Long id,Cliente cliente) {
 		 Cliente clienteAtual = _repositorioCliente.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Cliente não encontrado(a) pelo ID:" + id));
 		 if (cliente.getEndereco() != null) {
 			 autocompletarEndereco(cliente.getEndereco());
 			 validarEndereco(cliente.getEndereco());
 		 }
 		 cliente.setId(id);
 		 cliente.setCpfOuCnpj(clienteAtual.getCpfOuCnpj());
 		 if (_repositorioCliente.findByEmail(cliente.getEmail()).isPresent()) {
			throw new ResourceBadRequestException("Já existe um cliente com esse email!");
			//Aqui lança uma exception informando que ja existe um usuario com este e-mail.
 		 }
 		 String senha = passwordEncoder.encode(cliente.getSenha());
 		 cliente.setSenha(senha);
 		 if (!clienteAtual.getEndereco().getId().equals(null))
			_repositorioEndereco.deleteById(clienteAtual.getEndereco().getId());
         return _repositorioCliente.save(cliente);
	 }

	 public void deletar(Long id) {
		 var cliente = _repositorioCliente.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Cliente não encontrado(a) pelo ID:" + id));
		 if (!cliente.getEndereco().getId().equals(null))
				this._repositorioEndereco.deleteById(cliente.getEndereco().getId());
		 this._repositorioCliente.deleteById(id);
		 
	 }
	 
	 private void validaCpf(String cpf) {
		    CPFValidator cpfValidator = new CPFValidator(); 
		    List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf); 
		    if (!erros.isEmpty())
		    	throw new ResourceBadRequestException("CPF é inválido!");
	 }	 
	 
	 private void validarEndereco(Endereco endereco) {
			Endereco enderecoCorreto = servicoCep.obterEnderecoPorCep(endereco.getCep());
			if (!endereco.getRua().equals(enderecoCorreto.getRua()) ) 
				throw new ResourceBadRequestException("A rua não conferi com seu CEP!");
			if (!endereco.getBairro().equals(enderecoCorreto.getBairro()) )
				throw new ResourceBadRequestException("O bairro não confere com seu CEP!");
			if (!endereco.getCidade().equals(enderecoCorreto.getCidade()) )
				throw new ResourceBadRequestException("A cidade não confere com seu CEP!");
			if (!endereco.getComplemento().equals(enderecoCorreto.getComplemento()) )
				throw new ResourceBadRequestException("O complemento não confere com seu CEP!");
			if (!endereco.getEstado().equals(enderecoCorreto.getEstado()) )
				throw new ResourceBadRequestException("O estado não confere com seu CEP!");	
		}
	 
	 	private void autocompletarEndereco(Endereco endereco) {
			Endereco enderecoCorreto = servicoCep.obterEnderecoPorCep(endereco.getCep());
			if (endereco.getRua() == null ) 
				endereco.setRua(enderecoCorreto.getRua());
			if (endereco.getBairro() == null)
				endereco.setBairro(enderecoCorreto.getBairro());
			if (endereco.getCidade() == null )
				endereco.setCidade(enderecoCorreto.getCidade());
			if (endereco.getComplemento() == null)
				endereco.setComplemento(enderecoCorreto.getComplemento());
			if (endereco.getEstado() == null )
				endereco.setEstado(enderecoCorreto.getEstado());
		}
	 	
	 	private void enviarEmailCadastro(Cliente cliente){
	 		var destinatarios = new ArrayList<String>();
			destinatarios.add("labratinformatica@gmail.com");
			destinatarios.add(cliente.getEmail());
			String html = "<html>"
					+ "<head>"
					+ "<title>Lab Rat Eletronicos</title>"
					+ "</head>"
					+ "<body style=\"text-align: center; font-family: Verdana, Geneva, Tahoma, sans-serif\" > "
					+ "<header style=\"background-color: #062035; color: white\"> "
					+ "<img src=\'https://i.ibb.co/ccyhrhC/logo.png\' alt=\"\" /><div>"
					+ "<h1>Prezado(a) "+ cliente.getNome()+"</h1>"
							+ "<h2> Seu cadastro foi efetuado com sucesso!</h2><br></div>"
							+ "<div style=\"color:#062035; background-color: white;\">"
									+ "<div style=\"color:white; background-color: #062035;\"></div>"
											+ "</body>"
											+ "</html>";

			var email  = new MensagemEmail("Seu cadastro foi concluido com sucesso!",
											html,	   
										   "Lab Rat Eletronicos <labratinformatica@gmail.com>",
										   destinatarios);
			_serviceEmail.enviarHtml(email);
		}
	 	
	 	public LoginResponse logar(String email, String senha) {
			
			Authentication autenticacao = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(email, senha, Collections.emptyList()));
			
			SecurityContextHolder.getContext().setAuthentication(autenticacao);
			
			String token = headerPrefix + jwtService.gerarToken(autenticacao);
			
			var cliente = _repositorioCliente.findByEmail(email);
			
			return new LoginResponse(token, cliente.get());
		}
}
