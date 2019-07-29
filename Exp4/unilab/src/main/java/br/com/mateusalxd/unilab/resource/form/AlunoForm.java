package br.com.mateusalxd.unilab.resource.form;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Aluno;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para cadastro de um novo Aluno")
public class AlunoForm {

	@ApiModelProperty(value = "Nome do aluno", example = "Mateus")
	private String nome;
	@ApiModelProperty(value = "Data de nascimento no formato dd/MM/yyyy", example = "01/12/1980")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	@ApiModelProperty(value = "Matrícula do aluno", example = "A0000001")
	private String matricula;
	@ApiModelProperty(value = "Endereço completo do aluno", example = "Rua 1, Centro, São Paulo, SP, 99999-999")
	private String endereco;
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

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
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

	public Aluno converter() {
		Aluno aluno = new Aluno();
		aluno.setDataNascimento(this.dataNascimento);
		aluno.setEndereco(this.endereco);
		aluno.setMatricula(this.matricula);
		aluno.setNome(this.nome);
		aluno.setTelefone(this.telefone);
		aluno.setUsuario(null);
		
		return aluno;
	}

}
