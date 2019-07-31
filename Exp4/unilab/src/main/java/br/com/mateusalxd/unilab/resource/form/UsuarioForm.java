package br.com.mateusalxd.unilab.resource.form;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Aluno;
import br.com.mateusalxd.unilab.model.Professor;
import br.com.mateusalxd.unilab.model.Usuario;
import br.com.mateusalxd.unilab.repository.AlunoRepository;
import br.com.mateusalxd.unilab.repository.PerfilRepository;
import br.com.mateusalxd.unilab.repository.ProfessorRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para cadastro de um novo Usuário")
public class UsuarioForm {

	@NotNull
	@NotEmpty
	@Size(max = 200)
	@ApiModelProperty(value = "Nome do usuário, representado pela matrícula do aluno ou o código do funcionário", example = "A0000001")
	private String nome;

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

	public Usuario converter(PerfilRepository perfilRepository, AlunoRepository alunoRepository,
			ProfessorRepository professorRepository) {
		

		Usuario usuario = new Usuario();
		usuario.setAtivo(this.ativo);
		usuario.setBloqueado(this.bloqueado);
		usuario.setDataCadastro(LocalDateTime.now());
		usuario.setDataInativacao(this.dataInativacao);
		usuario.setNome(this.nome);
		usuario.setSenha(new BCryptPasswordEncoder().encode(this.senha));
		usuario.setPerfis(this.perfis.stream()
				.map(perfilForm -> perfilRepository.findByNome(perfilForm.getNome()))
				.flatMap(perfil -> perfil.isPresent() ? Stream.of(perfil.get()) : Stream.empty())
				.collect(Collectors.toList()));

		Optional<Aluno> optionalAluno = alunoRepository.findByMatricula(this.nome);
		if (optionalAluno.isPresent()) {
			optionalAluno.get().setUsuario(usuario);
		} else {
			Optional<Professor> optionalProfessor = professorRepository.findByCodigo(this.nome);
			optionalProfessor.get().setUsuario(usuario);
		}

		return usuario;
	}

}
