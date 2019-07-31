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

import br.com.mateusalxd.unilab.model.Aluno;
import br.com.mateusalxd.unilab.model.Professor;
import br.com.mateusalxd.unilab.model.Usuario;
import br.com.mateusalxd.unilab.repository.AlunoRepository;
import br.com.mateusalxd.unilab.repository.PerfilRepository;
import br.com.mateusalxd.unilab.repository.ProfessorRepository;
import br.com.mateusalxd.unilab.repository.UsuarioRepository;
import br.com.mateusalxd.unilab.resource.dto.UsuarioDTO;
import br.com.mateusalxd.unilab.resource.form.AtualizacaoUsuarioForm;
import br.com.mateusalxd.unilab.resource.form.UsuarioForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("usuarios")
@Api(tags = "Usuário")
public class UsuarioResource {

	public static final String RECURSO_BASE = "usuarios";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve uma lista de Usuários")
	public ResponseEntity<List<UsuarioDTO>> listar() {
		return ResponseEntity.ok(UsuarioDTO.converter(usuarioRepository.findAll()));
	}

	@GetMapping(path = "{nome}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve um Usuário")
	public ResponseEntity<UsuarioDTO> exibir(
			@PathVariable @ApiParam(value = "Nome do usuário, representado pela matrícula do aluno ou o código do funcionário") String nome) {
		Optional<Usuario> optional = usuarioRepository.findByNome(nome);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new UsuarioDTO(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Cadastra um novo Usuário", notes = "A API devolve o local do recurso criado no cabeçalho de resposta")
	public ResponseEntity<UsuarioDTO> cadastrar(
			@RequestBody @Valid @ApiParam(value = "Dados do usuário") UsuarioForm formulario,
			@ApiParam(hidden = true) UriComponentsBuilder uriComponentsBuilder) {
		Usuario usuario = usuarioRepository
				.save(formulario.converter(perfilRepository, alunoRepository, professorRepository));

		URI uri = uriComponentsBuilder.path(UsuarioResource.RECURSO_BASE + "/{nome}").buildAndExpand(usuario.getNome())
				.toUri();
		return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
	}

	@DeleteMapping(path = "{nome}")
	@Transactional
	@ApiOperation(value = "Remove um Usuário")
	public ResponseEntity<?> remover(
			@PathVariable @ApiParam(value = "Nome do usuário, representado pela matrícula do aluno ou o código do funcionário") String nome) {
		Optional<Aluno> optionalAluno = alunoRepository.findByMatricula(nome);
		if (optionalAluno.isPresent()) {
			optionalAluno.get().setUsuario(null);
		} else {
			Optional<Professor> optionalProfessor = professorRepository.findByCodigo(nome);
			optionalProfessor.get().setUsuario(null);
		}

		Optional<Usuario> optional = usuarioRepository.findByNome(nome);
		if (optional.isPresent()) {
			usuarioRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping(path = "{nome}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Atualiza um Usuário")
	public ResponseEntity<UsuarioDTO> atualizar(
			@PathVariable @ApiParam(value = "Nome do usuário, representado pela matrícula do aluno ou o código do funcionário") String nome,
			@RequestBody @Valid @ApiParam(value = "Dados do usuário") AtualizacaoUsuarioForm formulario) {
		Optional<Usuario> optional = usuarioRepository.findByNome(nome);
		if (optional.isPresent()) {
			Usuario usuario = formulario.atualizar(optional.get(), perfilRepository);
			return ResponseEntity.ok(new UsuarioDTO(usuario));
		}

		return ResponseEntity.notFound().build();
	}

}
