package br.com.mateusalxd.unilab.resource.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para autenticação de um Usuário")
public class AutenticacaoForm {

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

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(this.nome, this.senha);
	}

}
