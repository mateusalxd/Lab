package br.com.mateusalxd.unilab.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de um Erro de Formulário")
public class ErroFormDTO {

	@ApiModelProperty(value = "Nome do campo", example = "nome")
	private String campo;
	@ApiModelProperty(value = "Mensagem de erro", example = "O campo nome é obrigatório")
	private String mensagem;

	public ErroFormDTO(String campo, String mensagem) {
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public final String getCampo() {
		return this.campo;
	}

	public final String getMensagem() {
		return this.mensagem;
	}

}
