package br.com.mateusalxd.produto.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 1000)
	private String descricao;
	private BigDecimal valor;
	@Column(length = 200)
	private String fornecedor;
	@Column(columnDefinition = "BOOLEAN")
	private Boolean usado;

	public Produto() {
	}

	public Produto(String descricao, BigDecimal valor, String fornecedor, Boolean usado) {
		this.descricao = descricao;
		this.valor = valor;
		this.fornecedor = fornecedor;
		this.usado = usado;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Boolean getUsado() {
		return this.usado;
	}

	public void setUsado(Boolean usado) {
		this.usado = usado;
	}

}
