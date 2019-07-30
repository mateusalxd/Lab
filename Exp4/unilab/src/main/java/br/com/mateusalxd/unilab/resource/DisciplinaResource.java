package br.com.mateusalxd.unilab.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import br.com.mateusalxd.unilab.model.Disciplina;
import br.com.mateusalxd.unilab.repository.DisciplinaRepository;
import br.com.mateusalxd.unilab.resource.dto.DisciplinaDTO;
import br.com.mateusalxd.unilab.resource.form.DisciplinaForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("disciplinas")
@Api(tags = "Disciplina")
public class DisciplinaResource {

	public static final String RECURSO_BASE = "disciplinas";

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve uma lista de Disciplinas")
	public ResponseEntity<List<DisciplinaDTO>> listar() {
		return ResponseEntity.ok(DisciplinaDTO.converter(disciplinaRepository.findAll()));
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve uma Disciplina")
	public ResponseEntity<DisciplinaDTO> exibir(
			@PathVariable @ApiParam(value = "Identificação da disciplina") Long id) {
		Optional<Disciplina> optional = disciplinaRepository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new DisciplinaDTO(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Cadastra uma nova Disciplina", notes = "A API devolve o local do recurso criado no cabeçalho de resposta")
	public ResponseEntity<DisciplinaDTO> cadastrar(
			@RequestBody @Valid @ApiParam(value = "Dados da disciplina") DisciplinaForm formulario,
			@ApiParam(hidden = true) UriComponentsBuilder uriComponentsBuilder) {
		Disciplina disciplina = disciplinaRepository.save(formulario.converter());

		URI uri = uriComponentsBuilder.path(DisciplinaResource.RECURSO_BASE + "/{id}")
				.buildAndExpand(disciplina.getId()).toUri();
		return ResponseEntity.created(uri).body(new DisciplinaDTO(disciplina));
	}

	@DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Remove uma Disciplina")
	public ResponseEntity<?> remover(@PathVariable @ApiParam(value = "Identificação da disciplina") Long id) {
		Optional<Disciplina> optional = disciplinaRepository.findById(id);
		if (optional.isPresent()) {
			disciplinaRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Atualiza uma Disciplina")
	public ResponseEntity<DisciplinaDTO> atualizar(
			@PathVariable @ApiParam(value = "Identificação da disciplina") Long id,
			@RequestBody @Valid @ApiParam(value = "Dados da disciplina") DisciplinaForm formulario) {
		Optional<Disciplina> optional = disciplinaRepository.findById(id);
		if (optional.isPresent()) {
			Disciplina disciplina = formulario.atualizar(optional.get());
			return ResponseEntity.ok(new DisciplinaDTO(disciplina));
		}

		return ResponseEntity.notFound().build();
	}

}
