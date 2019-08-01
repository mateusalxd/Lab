package br.com.mateusalxd.unilab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.mateusalxd.unilab.filter.AutenticacaoTokenFilter;
import br.com.mateusalxd.unilab.repository.UsuarioRepository;
import br.com.mateusalxd.unilab.service.AutenticacaoService;
import br.com.mateusalxd.unilab.service.TokenService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/autenticacao").permitAll()
			.antMatchers("/**").hasAuthority("*_*")
			.antMatchers("/actuator/**").hasAuthority("actuator_*")
			
			.antMatchers(HttpMethod.DELETE, "/alunos/*").hasAuthority("alunos_delete")
			.antMatchers(HttpMethod.GET, "/alunos", "/alunos/*").hasAuthority("alunos_get")
			.antMatchers(HttpMethod.POST, "/alunos").hasAuthority("alunos_post")
			.antMatchers(HttpMethod.PUT, "/alunos/*").hasAuthority("alunos_put")
			
			.antMatchers(HttpMethod.DELETE, "/cursos/*").hasAuthority("cursos_delete")
			.antMatchers(HttpMethod.GET, "/cursos", "/cursos/*").hasAuthority("cursos_get")
			.antMatchers(HttpMethod.POST, "/cursos").hasAuthority("cursos_post")
			.antMatchers(HttpMethod.PUT, "/cursos/*").hasAuthority("cursos_put")
			
			.antMatchers(HttpMethod.DELETE, "/disciplinas/*").hasAuthority("disciplinas_delete")
			.antMatchers(HttpMethod.GET, "/disciplinas", "/disciplinas/*").hasAuthority("disciplinas_get")
			.antMatchers(HttpMethod.POST, "/disciplinas").hasAuthority("disciplinas_post")
			.antMatchers(HttpMethod.PUT, "/disciplinas/*").hasAuthority("disciplinas_put")		
			
			.antMatchers(HttpMethod.DELETE, "/perfis/*").hasAuthority("perfis_delete")
			.antMatchers(HttpMethod.GET, "/perfis", "/perfis/*").hasAuthority("perfis_get")
			.antMatchers(HttpMethod.POST, "/perfis").hasAuthority("perfis_post")
			.antMatchers(HttpMethod.PUT, "/perfis/*").hasAuthority("perfis_put")				
			
			.antMatchers(HttpMethod.DELETE, "/professores/*").hasAuthority("professores_delete")
			.antMatchers(HttpMethod.GET, "/professores", "/professores/*").hasAuthority("professores_get")
			.antMatchers(HttpMethod.POST, "/professores").hasAuthority("professores_post")
			.antMatchers(HttpMethod.PUT, "/professores/*").hasAuthority("professores_put")	
			
			.antMatchers(HttpMethod.DELETE, "/usuarios/*").hasAuthority("usuarios_delete")
			.antMatchers(HttpMethod.GET, "/usuarios", "/usuarios/*").hasAuthority("usuarios_get")
			.antMatchers(HttpMethod.POST, "/usuarios").hasAuthority("usuarios_post")
			.antMatchers(HttpMethod.PUT, "/usuarios/*").hasAuthority("usuarios_put")	
			
			.anyRequest().denyAll()
			.and().csrf().disable()
			
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and().addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class)
			;
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}
	
}
