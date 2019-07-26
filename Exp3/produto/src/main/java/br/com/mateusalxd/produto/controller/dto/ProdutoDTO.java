package br.com.mateusalxd.produto.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.mateusalxd.produto.model.Produto;

public class ProdutoDTO {

	private final Long id;
	private final String descricao;
	private final String fornecedor;
	private final BigDecimal valor;

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.descricao = produto.getDescricao();
		this.fornecedor = produto.getFornecedor();
		this.valor = produto.getValor();
	}

	public Long getId() {
		return this.id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public String getFornecedor() {
		return this.fornecedor;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public static List<ProdutoDTO> converter(List<Produto> produtos) {
		return produtos.stream().map(ProdutoDTO::new).collect(Collectors.toList());
	}

}
