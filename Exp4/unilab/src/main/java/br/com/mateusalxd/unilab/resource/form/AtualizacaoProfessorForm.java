package br.com.mateusalxd.unilab.resource.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Professor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para atualização de um Professor")
public class AtualizacaoProfessorForm {

	@NotNull
	@NotEmpty
	@Size(max = 200)
	@ApiModelProperty(value = "Nome do professor", example = "Ricardo")
	private String nome;

	@NotNull
	@ApiModelProperty(value = "Data de nascimento no formato dd/MM/yyyy", example = "15/06/1960")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	@NotNull
	@NotEmpty
	@Size(max = 100)
	@ApiModelProperty(value = "Cargo do professor", example = "Professor substituto")
	private String cargo;

	@NotNull
	@ApiModelProperty(value = "Salário do professor", example = "1000.00")
	private BigDecimal salario;

	public final String getNome() {
		return this.nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

	public final LocalDate getDataNascimento() {
		return this.dataNascimento;
	}

	public final void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public final String getCargo() {
		return this.cargo;
	}

	public final void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public final BigDecimal getSalario() {
		return this.salario;
	}

	public final void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Professor atualizar(Professor professor) {
		professor.setNome(this.nome);
		professor.setDataNascimento(this.dataNascimento);
		professor.setCargo(this.cargo);
		professor.setSalario(this.salario);

		return professor;
	}

}
