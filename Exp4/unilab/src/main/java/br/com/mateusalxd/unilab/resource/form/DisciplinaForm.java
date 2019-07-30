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

}
