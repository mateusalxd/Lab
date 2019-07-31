package br.com.mateusalxd.unilab.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.mateusalxd.unilab.model.Usuario;
import br.com.mateusalxd.unilab.repository.UsuarioRepository;
import br.com.mateusalxd.unilab.service.TokenService;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UsuarioRepository usuarioRepository;

	public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		boolean tokenValido = tokenService.isTokenValido(token);
		if (tokenValido) {
			autenticar(token);
		}

		filterChain.doFilter(request, response);
	}

	private void autenticar(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
		if (optional.isPresent()) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(optional.get(),
					null, optional.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
