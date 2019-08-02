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
import br.com.mateusalxd.unilab.repository.UsuarioRepository;
import br.com.mateusalxd.unilab.resource.dto.AlunoDTO;
import br.com.mateusalxd.unilab.resource.form.AlunoForm;
import br.com.mateusalxd.unilab.resource.form.AtualizacaoAlunoForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("alunos")
@Api(tags = "Aluno")
public class AlunoResource {

	public static final String RECURSO_BASE = "alunos";

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve uma lista de Alunos")
	public ResponseEntity<List<AlunoDTO>> listar() {
		return ResponseEntity.ok(AlunoDTO.converter(alunoRepository.findAll()));
	}

	@GetMapping(path = "{matricula}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve um Aluno")
	public ResponseEntity<AlunoDTO> exibir(@PathVariable @ApiParam(value = "Matrícula do aluno") String matricula) {
		Optional<Aluno> optional = alunoRepository.findByMatricula(matricula);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new AlunoDTO(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Cadastra um novo Aluno", notes = "A API devolve o local do recurso criado no cabeçalho de resposta")
	public ResponseEntity<AlunoDTO> cadastrar(
			@RequestBody @Valid @ApiParam(value = "Dados do aluno") AlunoForm formulario,
			@ApiParam(hidden = true) UriComponentsBuilder uriComponentsBuilder) {
		Optional<Professor> optional = professorRepository.findByCodigo(formulario.getMatricula());
		if (optional.isPresent()) {
			throw new DataIntegrityViolationException("A matrícula já foi utilizada como códido de Professor");
		}

		Aluno aluno = alunoRepository.save(formulario.converter());

		URI uri = uriComponentsBuilder.path(AlunoResource.RECURSO_BASE + "/{matricula}").buildAndExpand(aluno.getMatricula()).toUri();
		return ResponseEntity.created(uri).body(new AlunoDTO(aluno));
	}

	@DeleteMapping(path = "{matricula}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Remove um Aluno")
	public ResponseEntity<?> remover(@PathVariable @ApiParam(value = "Matrícula do aluno") String matricula) {
		Optional<Aluno> optional = alunoRepository.findByMatricula(matricula);
		if (optional.isPresent()) {
			usuarioRepository.delete(optional.get().getUsuario());
			alunoRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping(path = "{matricula}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Atualiza um Aluno")
	public ResponseEntity<AlunoDTO> atualizar(@PathVariable @ApiParam(value = "Matrícula do aluno") String matricula,
			@RequestBody @Valid @ApiParam(value = "Dados do aluno") AtualizacaoAlunoForm formulario) {
		Optional<Aluno> optional = alunoRepository.findByMatricula(matricula);
		if (optional.isPresent()) {
			Aluno aluno = formulario.atualizar(optional.get());
			return ResponseEntity.ok(new AlunoDTO(aluno));
		}

		return ResponseEntity.notFound().build();
	}

}
