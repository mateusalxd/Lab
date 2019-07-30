package br.com.mateusalxd.unilab.resource.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Professor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de um Professor")
public class ProfessorDTO {

	@ApiModelProperty(value = "Nome do professor", example = "Ricardo")
	private String nome;

	@ApiModelProperty(value = "Data de nascimento no formato dd/MM/yyyy", example = "15/06/1960")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	@ApiModelProperty(value = "Código do professor", example = "C0000001")
	private String codigo;

	@ApiModelProperty(value = "Cargo do professor", example = "Professor substituto")
	private String cargo;

	@ApiModelProperty(value = "Salário do professor", example = "1000.00")
	private BigDecimal salario;

	public ProfessorDTO(Professor professor) {
		this.nome = professor.getNome();
		this.dataNascimento = professor.getDataNascimento();
		this.codigo = professor.getCodigo();
		this.cargo = professor.getCargo();
		this.salario = professor.getSalario();
	}

	public String getNome() {
		return this.nome;
	}

	public LocalDate getDataNascimento() {
		return this.dataNascimento;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getCargo() {
		return this.cargo;
	}

	public BigDecimal getSalario() {
		return this.salario;
	}

	public static List<ProfessorDTO> converter(List<Professor> professores) {
		return professores.stream().map(ProfessorDTO::new).collect(Collectors.toList());
	}

}
