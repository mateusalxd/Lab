package br.com.mateusalxd.unilab.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mateusalxd.unilab.resource.dto.TokenDTO;
import br.com.mateusalxd.unilab.resource.form.AutenticacaoForm;
import br.com.mateusalxd.unilab.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("autenticacao")
@Api(tags = "Autentição")
public class AutenticacaoResource {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Autentica um Usuário")
	public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid @ApiParam(value = "Dados de autenticação") AutenticacaoForm formulario) {
		UsernamePasswordAuthenticationToken authenticationToken = formulario.converter();
		try {
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
