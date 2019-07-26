package br.com.mateusalxd.produto.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.mateusalxd.produto.model.Produto;
import br.com.mateusalxd.produto.repository.ProdutoRepository;

public class AtualizaProdutoForm {

	@NotNull
	@Length(min = 10, max = 1000)
	private String descricao;
	@NotNull
	private BigDecimal valor;
	@NotNull
	@Length(min = 10, max = 200)
	private String fornecedor;

	public AtualizaProdutoForm() {
	}

	public AtualizaProdutoForm(Produto produto) {
		this.descricao = produto.getDescricao();
		this.valor = produto.getValor();
		this.fornecedor = produto.getFornecedor();
	}

	public final String getDescricao() {
		return descricao;
	}

	public final void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public final BigDecimal getValor() {
		return valor;
	}

	public final void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public final String getFornecedor() {
		return fornecedor;
	}

	public final void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public void atualizar(Produto produto, ProdutoRepository produtoRepository) {
		produto.setDescricao(this.descricao);
		produto.setFornecedor(this.fornecedor);
		produto.setValor(this.valor);
	}

}
