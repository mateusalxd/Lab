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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		CursoDTO other = (CursoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
