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

import br.com.mateusalxd.unilab.model.Curso;
import br.com.mateusalxd.unilab.repository.CursoRepository;
import br.com.mateusalxd.unilab.resource.dto.CursoDTO;
import br.com.mateusalxd.unilab.resource.form.CursoForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("cursos")
@Api(tags = "Curso")
public class CursoResource {

	public static final String RECURSO_BASE = "cursos";

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve uma lista de Cursos")
	public ResponseEntity<List<CursoDTO>> listar() {
		return ResponseEntity.ok(CursoDTO.converter(cursoRepository.findAll()));
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve um Curso")
	public ResponseEntity<CursoDTO> exibir(@PathVariable @ApiParam(value = "Identificação do curso") Long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new CursoDTO(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Cadastra um novo Curso", notes = "A API devolve o local do recurso criado no cabeçalho de resposta")
	public ResponseEntity<CursoDTO> cadastrar(
			@RequestBody @Valid @ApiParam(value = "Dados do curso") CursoForm formulario,
			@ApiParam(hidden = true) UriComponentsBuilder uriComponentsBuilder) {
		Curso curso = cursoRepository.save(formulario.converter());

		URI uri = uriComponentsBuilder.path(CursoResource.RECURSO_BASE + "/{id}").buildAndExpand(curso.getId()).toUri();
		return ResponseEntity.created(uri).body(new CursoDTO(curso));
	}

	@DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Remove um Curso")
	public ResponseEntity<?> remover(@PathVariable @ApiParam(value = "Identificação do curso") Long id) {
		Optional<Curso> optional = cursoRepository.findById(id);
		if (optional.isPresent()) {
			cursoRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Atualiza um Curso")
	public ResponseEntity<CursoDTO> atualizar(@PathVariable @ApiParam(value = "Identificação do curso") Long id,
			@RequestBody @Valid @ApiParam(value = "Dados do curso") CursoForm formulario) {
		Optional<Curso> optional = cursoRepository.findById(id);
		if (optional.isPresent()) {
			Curso curso = formulario.atualizar(optional.get());
			return ResponseEntity.ok(new CursoDTO(curso));
		}

		return ResponseEntity.notFound().build();
	}

}
