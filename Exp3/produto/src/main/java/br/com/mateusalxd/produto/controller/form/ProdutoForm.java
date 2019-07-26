package br.com.mateusalxd.produto.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.mateusalxd.produto.model.Produto;

public class ProdutoForm {

	@NotNull
	@Length(min = 10, max = 1000)
	private String descricao;
	@NotNull
	private BigDecimal valor;
	@NotNull
	@Length(min = 10, max = 200)
	private String fornecedor;
	@NotNull
	private Boolean usado;

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public void setUsado(Boolean usado) {
		this.usado = usado;
	}

	public Produto converter() {

		return new Produto(this.descricao, this.valor, this.fornecedor, this.usado);
	}

}
