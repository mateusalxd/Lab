package br.com.mateusalxd.unilab.resource.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.mateusalxd.unilab.model.Curso;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de um Curso")
public class CursoDTO {

	@ApiModelProperty(value = "Identificação do curso", example = "1")
	private Long id;

	@ApiModelProperty(value = "Nome do curso", example = "Ciência da Computação")
	private String nome;

	public CursoDTO(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public static List<CursoDTO> converter(List<Curso> cursos) {
		return cursos.stream().map(CursoDTO::new).collect(Collectors.toList());
	}

}
