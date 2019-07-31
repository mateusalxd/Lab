package br.com.mateusalxd.unilab.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import br.com.mateusalxd.unilab.model.Aluno;
import br.com.mateusalxd.unilab.model.Professor;
import br.com.mateusalxd.unilab.repository.AlunoRepository;
import br.com.mateusalxd.unilab.repository.ProfessorRepository;
import br.com.mateusalxd.unilab.resource.dto.ProfessorDTO;
import br.com.mateusalxd.unilab.resource.form.AtualizacaoProfessorForm;
import br.com.mateusalxd.unilab.resource.form.ProfessorForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("professores")
@Api(tags = "Professor")
public class ProfessorResource {

	public static final String RECURSO_BASE = "professores";

	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve uma lista de Professores")
	public ResponseEntity<List<ProfessorDTO>> listar() {
		return ResponseEntity.ok(ProfessorDTO.converter(professorRepository.findAll()));
	}

	@GetMapping(path = "{codigo}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve um Professor")
	public ResponseEntity<ProfessorDTO> exibir(@PathVariable @ApiParam(value = "Código do professor") String codigo) {
		Optional<Professor> optional = professorRepository.findByCodigo(codigo);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new ProfessorDTO(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Cadastra um novo Professor", notes = "A API devolve o local do recurso criado no cabeçalho de resposta")
	public ResponseEntity<ProfessorDTO> cadastrar(
			@RequestBody @Valid @ApiParam(value = "Dados do professor") ProfessorForm formulario,
			@ApiParam(hidden = true) UriComponentsBuilder uriComponentsBuilder) {
		Optional<Aluno> optional = alunoRepository.findByMatricula(formulario.getCodigo());
		if (optional.isPresent()) {
			throw new DataIntegrityViolationException("O código já foi utilizado como matrícula de Aluno");
		}
		
		Professor professor = professorRepository.save(formulario.converter());

		URI uri = uriComponentsBuilder.path(ProfessorResource.RECURSO_BASE + "/{id}").buildAndExpand(professor.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new ProfessorDTO(professor));
	}

	@DeleteMapping(path = "{codigo}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Remove um Professor")
	public ResponseEntity<?> remover(@PathVariable @ApiParam(value = "Matrícula do professor") String codigo) {
		Optional<Professor> optional = professorRepository.findByCodigo(codigo);
		if (optional.isPresent()) {
			professorRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping(path = "{codigo}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Atualiza um Professor")
	public ResponseEntity<ProfessorDTO> atualizar(
			@PathVariable @ApiParam(value = "Código do professor") String codigo,
			@RequestBody @Valid @ApiParam(value = "Dados do professor") AtualizacaoProfessorForm formulario) {
		Optional<Professor> optional = professorRepository.findByCodigo(codigo);
		if (optional.isPresent()) {
			Professor professor = formulario.atualizar(optional.get());
			return ResponseEntity.ok(new ProfessorDTO(professor));
		}

		return ResponseEntity.notFound().build();
	}

}
