package br.com.mateusalxd.unilab.resource.form;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Aluno;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para atualização de um Aluno")
public class AtualizacaoAlunoForm {

	@NotNull
	@NotEmpty
	@Size(max = 200)
	@ApiModelProperty(value = "Nome do aluno", example = "Mateus")
	private String nome;

	@NotNull
	@ApiModelProperty(value = "Data de nascimento no formato dd/MM/yyyy", example = "01/12/1980")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	@NotNull
	@NotEmpty
	@Size(max = 500)
	@ApiModelProperty(value = "Endereço completo do aluno", example = "Rua 1, Centro, São Paulo, SP, 99999-999")
	private String endereco;

	@Size(max = 20)
	@ApiModelProperty(value = "Telefone do aluno", example = "19-99999-9999")
	private String telefone;

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Aluno atualizar(Aluno aluno) {
		aluno.setNome(this.nome);
		aluno.setDataNascimento(this.dataNascimento);
		aluno.setEndereco(this.endereco);
		aluno.setTelefone(this.telefone);

		return aluno;
	}

}
