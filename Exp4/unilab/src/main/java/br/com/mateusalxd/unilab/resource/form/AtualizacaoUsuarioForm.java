package br.com.mateusalxd.unilab.resource.form;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Usuario;
import br.com.mateusalxd.unilab.repository.PerfilRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para atualização de um Usuário")
public class AtualizacaoUsuarioForm {

	@NotNull
	@NotEmpty
	@Size(max = 1000)
	@ApiModelProperty(value = "Senha do usuário", example = "SeNhAUlTrAsEcReTa123")
	private String senha;

	@NotNull
	@ApiModelProperty(value = "Indica se o usuário está ativo", example = "true")
	private Boolean ativo;

	@NotNull
	@ApiModelProperty(value = "Indica se o usuário está bloqueado", example = "false")
	private Boolean bloqueado;

	@ApiModelProperty(value = "Data de inativação do usuário no formato dd/MM/yyyy HH:mm:ss", example = "01/07/2019 11:58:00", required = false)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataInativacao;

	@ApiModelProperty(value = "Perfis atribuídos ao usuário", example = "[{\"nome\":\"alunos_get\"}]", required = false)
	private List<PerfilForm> perfis;

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

	public LocalDateTime getDataInativacao() {
		return this.dataInativacao;
	}

	public void setDataInativacao(LocalDateTime dataInativacao) {
		this.dataInativacao = dataInativacao;
	}

	public List<PerfilForm> getPerfis() {
		return this.perfis;
	}

	public void setPerfis(List<PerfilForm> perfis) {
		this.perfis = perfis;
	}

	public Usuario atualizar(Usuario usuario, PerfilRepository perfilRepository) {
		if (this.ativo && this.dataInativacao.compareTo(LocalDateTime.now()) > 0) {
			usuario.setAtivo(false);
		} else {
			usuario.setAtivo(this.ativo);
		}
		usuario.setBloqueado(this.bloqueado);
		usuario.setDataInativacao(this.dataInativacao);
		usuario.setSenha(new BCryptPasswordEncoder().encode(this.senha));
		usuario.setPerfis(this.perfis.stream()
				.map(perfilForm -> perfilRepository.findByNome(perfilForm.getNome()))
				.flatMap(perfil -> perfil.isPresent() ? Stream.of(perfil.get()) : Stream.empty())
				.collect(Collectors.toList()));

		return usuario;
	}

}
