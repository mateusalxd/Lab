package br.com.mateusalxd.unilab.resource.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.mateusalxd.unilab.model.Perfil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para cadastro/atualização de um novo Perfil")
public class PerfilForm {

	@NotNull
	@NotEmpty
	@Size(max = 200)
	@ApiModelProperty(value = "Nome do perfil, composto por caminho_método", example = "perfis_get")
	private String nome;

	public PerfilForm() {
	}

	public PerfilForm(Perfil perfil) {
		this.nome = perfil.getNome();
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Perfil atualizar(Perfil perfil) {
		perfil.setNome(this.nome);

		return perfil;
	}

	public Perfil converter() {
		Perfil perfil = new Perfil();
		perfil.setNome(this.nome);

		return perfil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PerfilForm other = (PerfilForm) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
