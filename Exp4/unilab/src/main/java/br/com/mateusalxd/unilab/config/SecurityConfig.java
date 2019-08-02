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
			.antMatchers("/h2-console", "/h2-console/**").permitAll()
			
			.antMatchers("/actuator/**").hasAuthority("actuator_*")
			
			.antMatchers(HttpMethod.DELETE, "/alunos/*").hasAnyAuthority("alunos_delete", "*_*")
			.antMatchers(HttpMethod.GET, "/alunos", "/alunos/*").hasAnyAuthority("alunos_get", "*_*")
			.antMatchers(HttpMethod.POST, "/alunos").hasAnyAuthority("alunos_post", "*_*")
			.antMatchers(HttpMethod.PUT, "/alunos/*").hasAnyAuthority("alunos_put", "*_*")
			
			.antMatchers(HttpMethod.DELETE, "/cursos/*").hasAnyAuthority("cursos_delete", "*_*")
			.antMatchers(HttpMethod.GET, "/cursos", "/cursos/*").hasAnyAuthority("cursos_get", "*_*")
			.antMatchers(HttpMethod.POST, "/cursos").hasAnyAuthority("cursos_post", "*_*")
			.antMatchers(HttpMethod.PUT, "/cursos/*").hasAnyAuthority("cursos_put", "*_*")
			
			.antMatchers(HttpMethod.DELETE, "/disciplinas/*").hasAnyAuthority("disciplinas_delete", "*_*")
			.antMatchers(HttpMethod.GET, "/disciplinas", "/disciplinas/*").hasAnyAuthority("disciplinas_get", "*_*")
			.antMatchers(HttpMethod.POST, "/disciplinas").hasAnyAuthority("disciplinas_post", "*_*")
			.antMatchers(HttpMethod.PUT, "/disciplinas/*").hasAnyAuthority("disciplinas_put", "*_*")
			
			.antMatchers(HttpMethod.DELETE, "/perfis/*").hasAnyAuthority("perfis_delete", "*_*")
			.antMatchers(HttpMethod.GET, "/perfis", "/perfis/*").hasAnyAuthority("perfis_get", "*_*")
			.antMatchers(HttpMethod.POST, "/perfis").hasAnyAuthority("perfis_post", "*_*")
			.antMatchers(HttpMethod.PUT, "/perfis/*").hasAnyAuthority("perfis_put", "*_*")		
			
			.antMatchers(HttpMethod.DELETE, "/professores/*").hasAnyAuthority("professores_delete", "*_*")
			.antMatchers(HttpMethod.GET, "/professores", "/professores/*").hasAnyAuthority("professores_get", "*_*")
			.antMatchers(HttpMethod.POST, "/professores").hasAnyAuthority("professores_post", "*_*")
			.antMatchers(HttpMethod.PUT, "/professores/*").hasAnyAuthority("professores_put", "*_*")
			
			.antMatchers(HttpMethod.DELETE, "/usuarios/*").hasAnyAuthority("usuarios_delete", "*_*")
			.antMatchers(HttpMethod.GET, "/usuarios", "/usuarios/*").hasAnyAuthority("usuarios_get", "*_*")
			.antMatchers(HttpMethod.POST, "/usuarios").hasAnyAuthority("usuarios_post", "*_*")
			.antMatchers(HttpMethod.PUT, "/usuarios/*").hasAnyAuthority("usuarios_put", "*_*")
			
			.antMatchers("/**").hasAuthority("*_*")
			
			.anyRequest().denyAll()
			.and().csrf().disable()
			
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and().addFilterBefore(new AutenticacaoTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class)
			;
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**", "/h2-console/**");
	}
	
}
