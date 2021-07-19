package br.com.serratecEcommerce.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.serratecEcommerce.model.Categoria;
import br.com.serratecEcommerce.model.Produto;
import br.com.serratecEcommerce.model.ProdutoRequest;
import br.com.serratecEcommerce.model.exception.ResourceBadRequestException;
import br.com.serratecEcommerce.model.exception.ResourceNotFoundException;
import br.com.serratecEcommerce.repository.CategoriaRepository;
import br.com.serratecEcommerce.repository.ProdutoRepository;


@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository _repositorioProduto;
	
	@Autowired
	private CategoriaRepository _repositorioCategoria;
		
	@Autowired
	private UploadService servicoUpload;
	
	public List<Produto> obterTodos(){
		return this._repositorioProduto.findAll();
		}
	
	public Produto obterPorId(Long id){
		return _repositorioProduto.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Produto não encontrada pelo ID:" + id));
	}
	
	public List<Produto> obterPorNome(String nome){
		List<Produto> produtos = _repositorioProduto.findByNomeContaining(nome);
		if (produtos.isEmpty())
			throw new ResourceNotFoundException("Produto não encontrada pelo nome:" + nome);
		return produtos;
	}
	
	public ResponseEntity<Produto> adicionar(ProdutoRequest produtoRequest){
		Produto produto = new Produto();
		Long categoriaId = produtoRequest.getCategoriaId();
		if (categoriaId != null) {
			Optional<Categoria> categoria = _repositorioCategoria.findById(categoriaId);
			if (categoria.isEmpty())
				throw new ResourceNotFoundException("Categoria não encontrada pelo ID:" + categoriaId);
			produto.setCategoria(categoria.get());
		}
		if (produtoRequest.getNome() != null)
			produto.setNome(produtoRequest.getNome());
		if (produtoRequest.getDescricao() != null)
			produto.setDescricao(produtoRequest.getDescricao());
		if (produtoRequest.getPreco() != null)
			produto.setPreco(produtoRequest.getPreco());
		if (produtoRequest.getQuantidadeEmEstoque() != null)
			produto.setQuantidadeEmEstoque(produtoRequest.getQuantidadeEmEstoque());
		this.validarProduto(produto);
		produto.setId(null);
		produto.setDataDeCadastroDoProduto(new Date());
		var adicionado = _repositorioProduto.save(produto);
        return new ResponseEntity<>(adicionado, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Produto> adicionarImagemAoProduto(Long id, MultipartFile imagem){
		Produto produto = _repositorioProduto.findById(id).orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado pelo ID:" + id));
		String enderecoImagem = servicoUpload.salvar("/img", imagem);
		produto.setImagem(enderecoImagem);
		var atualizado = _repositorioProduto.save(produto);
		return new ResponseEntity<>(atualizado, HttpStatus.CREATED);
	}
	
	 public Produto atualizar(Long id,ProdutoRequest produtoRequest) {
 		Produto produto = _repositorioProduto.findById(id).orElseThrow( () -> new ResourceNotFoundException("Produto não encontrado pelo ID:" + id));
		Long categoriaId = produtoRequest.getCategoriaId();
		if (categoriaId != null) {
			var categoria = _repositorioCategoria.findById(categoriaId);
			if (categoria.isEmpty())
				throw new ResourceNotFoundException("Categoria não encontrada pelo ID:" + categoriaId);
			produto.setCategoria(categoria.get());
		}
		if (produtoRequest.getNome() != null)
			produto.setNome(produtoRequest.getNome());
		if (produtoRequest.getDescricao() != null)
			produto.setDescricao(produtoRequest.getDescricao());
		if (produtoRequest.getPreco() != null)
			produto.setPreco(produtoRequest.getPreco());
		if (produtoRequest.getQuantidadeEmEstoque() != null)
			produto.setQuantidadeEmEstoque(produtoRequest.getQuantidadeEmEstoque());
 		this.validarProduto(produto);
 		produto.setId(id);
        return this._repositorioProduto.save(produto);
	 }
		
	 public void deletar(Long id) {
		 _repositorioProduto.findById(id).orElseThrow( () -> new ResourceNotFoundException("Produto não encontrada pelo ID:" + id));
         this._repositorioProduto.deleteById(id);
	 }
	 
	 private void validarProduto(Produto produto) {
		 if (produto.getQuantidadeEmEstoque() < 0)
			 throw new ResourceBadRequestException("A quantidade em estoque é negativa!");
		 if (produto.getPreco() < 0)
			 throw new ResourceBadRequestException("O preço do produto é negativo!");
	 }
}
