package br.com.mateusalxd.produto.config.validation;

public class ErroValidacaoFormularioDTO {

	private final String campo;
	private final String mensagem;

	public ErroValidacaoFormularioDTO(String campo, String mensagem) {
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}

}
