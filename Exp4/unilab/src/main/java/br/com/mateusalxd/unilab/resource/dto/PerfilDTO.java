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

	public final Long getId() {
		return this.id;
	}

	public final String getNome() {
		return this.nome;
	}

	public static List<PerfilDTO> converter(List<Perfil> perfis) {
		return perfis.stream().map(PerfilDTO::new).collect(Collectors.toList());
	}

}
