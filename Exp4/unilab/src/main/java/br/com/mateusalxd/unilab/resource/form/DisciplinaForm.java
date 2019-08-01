package br.com.mateusalxd.unilab.resource.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.mateusalxd.unilab.model.Disciplina;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para cadastro/atualização de uma Disciplina")
public class DisciplinaForm {

	@NotNull
	@NotEmpty
	@Size(max = 200)
	@ApiModelProperty(value = "Nome da disciplina", example = "Cálculo I")
	private String nome;

	public String getNome() {
		return this.nome;
	}

	public Disciplina converter() {
		Disciplina disciplina = new Disciplina();
		disciplina.setNome(this.nome);

		return disciplina;
	}

	public Disciplina atualizar(Disciplina disciplina) {
		disciplina.setNome(this.nome);

		return disciplina;
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
		DisciplinaForm other = (DisciplinaForm) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
