package br.com.mateusalxd.unilab.resource.dto;

public class ErroFormDTO {

	private String campo;

	private String mensagem;

	public ErroFormDTO(String campo, String mensagem) {
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return this.campo;
	}

	public String getMensagem() {
		return this.mensagem;
	}

}
