package br.com.mateusalxd.unilab.resource.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.mateusalxd.unilab.model.Disciplina;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de uma Disciplina")
public class DisciplinaDTO {

	@ApiModelProperty(value = "Identificação da disciplina", example = "1")
	private Long id;

	@ApiModelProperty(value = "Nome da disciplina", example = "Cálculo I")
	private String nome;

	public DisciplinaDTO(Disciplina disciplina) {
		this.id = disciplina.getId();
		this.nome = disciplina.getNome();
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public static List<DisciplinaDTO> converter(List<Disciplina> disciplinas) {
		return disciplinas.stream().map(DisciplinaDTO::new).collect(Collectors.toList());
	}

}
