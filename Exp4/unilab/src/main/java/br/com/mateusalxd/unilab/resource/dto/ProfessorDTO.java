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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((salario == null) ? 0 : salario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessorDTO other = (ProfessorDTO) obj;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (salario == null) {
			if (other.salario != null)
				return false;
		} else if (!salario.equals(other.salario))
			return false;
		return true;
	}

}
