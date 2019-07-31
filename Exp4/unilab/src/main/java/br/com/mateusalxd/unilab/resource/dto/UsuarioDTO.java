package br.com.mateusalxd.unilab.resource.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Usuario;
import br.com.mateusalxd.unilab.resource.form.PerfilForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de um Usuário")
public class UsuarioDTO {

	@ApiModelProperty(value = "Nome do usuário, representado pela matrícula do aluno ou o código do funcionário", example = "A0000001")
	private String nome;

	@ApiModelProperty(value = "Indica se o usuário está ativo", example = "true")
	private Boolean ativo;

	@ApiModelProperty(value = "Indica se o usuário está bloqueado", example = "false")
	private Boolean bloqueado;

	@ApiModelProperty(value = "Data de realização do cadastro do usuário no formato dd/MM/yyyy HH:mm:ss", example = "01/07/2019 11:58:00")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCadastro;

	@ApiModelProperty(value = "Data de inativação do usuário no formato dd/MM/yyyy HH:mm:ss", example = "01/07/2019 11:58:00", required = false)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataInativacao;

	@ApiModelProperty(value = "Perfis atribuídos ao usuário", example = "[{\"nome\":\"alunos_get\"}]")
	private List<PerfilForm> perfis;

	public UsuarioDTO(Usuario usuario) {
		this.nome = usuario.getNome();
		this.ativo = usuario.getAtivo();
		this.bloqueado = usuario.getBloqueado();
		this.dataCadastro = usuario.getDataCadastro();
		this.dataInativacao = usuario.getDataInativacao();
		this.perfis = usuario.getPerfis().stream().map(PerfilForm::new).collect(Collectors.toList());
	}

	public String getNome() {
		return this.nome;
	}

	public Boolean getAtivo() {
		return this.ativo;
	}

	public Boolean getBloqueado() {
		return this.bloqueado;
	}

	public LocalDateTime getDataCadastro() {
		return this.dataCadastro;
	}

	public LocalDateTime getDataInativacao() {
		return this.dataInativacao;
	}

	public List<PerfilForm> getPerfis() {
		return this.perfis;
	}

	public static List<UsuarioDTO> converter(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList());
	}

}
