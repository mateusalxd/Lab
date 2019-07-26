package br.com.mateusalxd.produto.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mateusalxd.produto.controller.dto.ProdutoDTO;
import br.com.mateusalxd.produto.controller.form.AtualizaProdutoForm;
import br.com.mateusalxd.produto.controller.form.ProdutoForm;
import br.com.mateusalxd.produto.model.Produto;
import br.com.mateusalxd.produto.repository.ProdutoRepository;

@RestController
@RequestMapping("produtos")
public class ProdutosController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping
	public List<ProdutoDTO> listar() {
		return ProdutoDTO.converter(produtoRepository.findAll());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ProdutoDTO> criar(@RequestBody @Valid ProdutoForm form, UriComponentsBuilder uriBuilder) {
		Produto produto = form.converter();
		produtoRepository.save(produto);
		URI uri = uriBuilder.path("produtos/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProdutoDTO(produto));
	}

	@GetMapping("{id}")
	public ResponseEntity<ProdutoDTO> detalhar(@PathVariable Long id) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new ProdutoDTO(optional.get()));
		}

		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent()) {
			produtoRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}

	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody AtualizaProdutoForm form) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent()) {
			form.atualizar(optional.get(), produtoRepository);
			return ResponseEntity.ok(new ProdutoDTO(optional.get()));
		}

		return ResponseEntity.badRequest().build();
	}

}
