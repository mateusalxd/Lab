package br.com.mateusalxd.unilab.resource.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.mateusalxd.unilab.model.Curso;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Formulário utilizado para cadastro/atualização de um novo Curso")
public class CursoForm {

	@NotNull
	@NotEmpty
	@Size(max = 200)
	@ApiModelProperty(value = "Nome do curso", example = "Ciência da Computação")
	private String nome;

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Curso converter() {
		Curso curso = new Curso();
		curso.setNome(this.nome);

		return curso;
	}

	public Curso atualizar(Curso curso) {
		curso.setNome(this.nome);

		return curso;
	}

}
