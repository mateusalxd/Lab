package br.com.mateusalxd.produto.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Produto {

	private int id;
	private String descricao;
	private double valor;

	public Produto() {
		this.id = -1;
		this.descricao = "";
		this.valor = 0;
	}

	public Produto(int id, String descricao, double valor) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
	}

	public Produto(String descricao, double valor) {
		this.id = -1;
		this.descricao = descricao;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
