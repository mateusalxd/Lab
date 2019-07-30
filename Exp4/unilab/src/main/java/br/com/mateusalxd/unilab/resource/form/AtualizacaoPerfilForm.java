package br.com.mateusalxd.unilab.resource.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.mateusalxd.unilab.model.Perfil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para atualização de um Perfil")
public class AtualizacaoPerfilForm {

	@NotNull
	@NotEmpty
	@Size(max = 200)
	@ApiModelProperty(value = "Nome do perfil, composto por caminho_método", example = "perfis_get")
	private String nome;

	public final String getNome() {
		return this.nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

	public Perfil atualizar(Perfil perfil) {
		perfil.setNome(this.nome);
		
		return perfil;
	}

}
