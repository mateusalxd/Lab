package br.com.mateusalxd.unilab.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.mateusalxd.unilab.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${unilab.jwts.chave}")
	private String chave;

	@Value("${unilab.jwts.duracao}")
	private String duracao;

	public String gerarToken(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		Date dataHoje = new Date();
		Date dataExpiracao = new Date(dataHoje.getTime() + Long.valueOf(duracao));

		return Jwts.builder().setId("API Rest da UNILAB")
				.setSubject(usuario.getId().toString())
				.setIssuedAt(dataHoje)
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, chave)
				.compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.chave).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		return Long.valueOf(Jwts.parser().setSigningKey(this.chave).parseClaimsJws(token).getBody().getSubject());
	}

}
