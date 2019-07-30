package br.com.mateusalxd.unilab.resource.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.mateusalxd.unilab.model.Perfil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para cadastro de um novo Perfil")
public class PerfilForm {

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

	public Perfil converter() {
		Perfil perfil = new Perfil();
		perfil.setNome(this.nome);

		return perfil;
	}

}
