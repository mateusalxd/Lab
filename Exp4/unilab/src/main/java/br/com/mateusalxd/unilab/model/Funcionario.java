package br.com.mateusalxd.unilab.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Funcionario extends Pessoa {

	@Column(length = 50, unique = true, nullable = false)
	private String codigo;

	@Column(length = 100, nullable = false)
	private String cargo;

	@Column(precision = 8, scale = 2, nullable = false)
	private BigDecimal salario;

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalario() {
		return this.salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

}
