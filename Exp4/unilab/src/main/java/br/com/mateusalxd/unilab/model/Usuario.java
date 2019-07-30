package br.com.mateusalxd.unilab.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200, unique = true, nullable = false)
	private String nome;

	@Column(length = 1000, nullable = false)
	private String senha;

	@Column(columnDefinition = "BOOLEAN", nullable = false)
	private Boolean ativo;

	@Column(columnDefinition = "BOOLEAN", nullable = false)
	private Boolean bloqueado;

	@Column(nullable = false)
	private LocalDateTime dataCadastro;

	private LocalDateTime dataInativacao;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Perfil> perfis;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getBloqueado() {
		return this.bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public LocalDateTime getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getDataInativacao() {
		return this.dataInativacao;
	}

	public void setDataInativacao(LocalDateTime dataInativacao) {
		this.dataInativacao = dataInativacao;
	}

	public final List<Perfil> getPerfis() {
		return this.perfis;
	}

	public final void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfis;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.nome;
	}

	@Override
	public boolean isAccountNonExpired() {
		if (this.dataInativacao == null) {
			return true;
		} else if (this.dataInativacao.compareTo(LocalDateTime.now()) <= 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.bloqueado;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isAccountNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.ativo;
	}

}
