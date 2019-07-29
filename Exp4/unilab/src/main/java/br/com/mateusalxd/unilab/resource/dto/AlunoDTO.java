package br.com.mateusalxd.unilab.resource.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mateusalxd.unilab.model.Aluno;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de um Aluno")
public class AlunoDTO {

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

	public AlunoDTO(Aluno aluno) {
		this.nome = aluno.getNome();
		this.dataNascimento = aluno.getDataNascimento();
		this.matricula = aluno.getMatricula();
		this.endereco = aluno.getEndereco();
		this.telefone = aluno.getTelefone();
	}

	public String getNome() {
		return this.nome;
	}

	public LocalDate getDataNascimento() {
		return this.dataNascimento;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public static List<AlunoDTO> converter(List<Aluno> alunos) {
		return alunos.stream().map(AlunoDTO::new).collect(Collectors.toList());
	}

}
