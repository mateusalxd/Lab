package br.com.mateusalxd.unilab.resource.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.mateusalxd.unilab.model.Perfil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de um Perfil")
public class PerfilDTO {

	@ApiModelProperty(value = "Identificação do perfil", example = "1")
	private Long id;

	@ApiModelProperty(value = "Nome do perfil, composto por caminho_método", example = "perfis_get")
	private String nome;

	public PerfilDTO(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public static List<PerfilDTO> converter(List<Perfil> perfis) {
		return perfis.stream().map(PerfilDTO::new).collect(Collectors.toList());
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
		PerfilDTO other = (PerfilDTO) obj;
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
