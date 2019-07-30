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

import br.com.mateusalxd.unilab.model.Perfil;
import br.com.mateusalxd.unilab.repository.PerfilRepository;
import br.com.mateusalxd.unilab.resource.dto.PerfilDTO;
import br.com.mateusalxd.unilab.resource.form.PerfilForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("perfis")
@Api(tags = "Perfil")
public class PerfilResource {

	public static final String RECURSO_BASE = "perfis";

	@Autowired
	private PerfilRepository perfilRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve uma lista de Perfis")
	public ResponseEntity<List<PerfilDTO>> listar() {
		return ResponseEntity.ok(PerfilDTO.converter(perfilRepository.findAll()));
	}

	@GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Devolve um Perfil")
	public ResponseEntity<PerfilDTO> exibir(@PathVariable @ApiParam(value = "Identificação do perfil") Long id) {
		Optional<Perfil> optional = perfilRepository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new PerfilDTO(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Cadastra um novo Perfil", notes = "A API devolve o local do recurso criado no cabeçalho de resposta")
	public ResponseEntity<PerfilDTO> cadastrar(@RequestBody @Valid @ApiParam(value = "Dados do perfil") PerfilForm formulario,
			@ApiParam(hidden = true) UriComponentsBuilder uriComponentsBuilder) {
		Perfil perfil = perfilRepository.save(formulario.converter());

		URI uri = uriComponentsBuilder.path(PerfilResource.RECURSO_BASE + "/{id}").buildAndExpand(perfil.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new PerfilDTO(perfil));
	}

	@DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Remove um Perfil")
	public ResponseEntity<?> remover(@PathVariable @ApiParam(value = "Identificação do perfil") Long id) {
		Optional<Perfil> optional = perfilRepository.findById(id);
		if (optional.isPresent()) {
			perfilRepository.delete(optional.get());
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.badRequest().build();
	}

	@PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@ApiOperation(value = "Atualiza um Perfil")
	public ResponseEntity<PerfilDTO> atualizar(@PathVariable @ApiParam(value = "Identificação do perfil") Long id,
			@RequestBody @Valid @ApiParam(value = "Dados do perfil") PerfilForm formulario) {
		Optional<Perfil> optional = perfilRepository.findById(id);
		if (optional.isPresent()) {
			Perfil perfil = formulario.atualizar(optional.get());
			return ResponseEntity.ok(new PerfilDTO(perfil));
		}

		return ResponseEntity.notFound().build();
	}

}
