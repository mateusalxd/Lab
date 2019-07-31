package br.com.mateusalxd.unilab.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Visualização utilizada na exibição de um Token")
public class TokenDTO {

	@ApiModelProperty(value = "Token gerado pela autenticação", example = "Ad][1]sdkm3485{].;.2#7621$#@JBH!$")
	private String token;

	@ApiModelProperty(value = "Tipo de autenticação", example = "Bearer")
	private String tipo;

	public TokenDTO(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
