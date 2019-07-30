package br.com.mateusalxd.unilab.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200, unique = true, nullable = false)
	private String nome;

	@Column(nullable = false)
	private Short periodo;

	@Column(nullable = false)
	private Short cargaHoraria;

	@ManyToMany(mappedBy = "disciplinas")
	private List<Curso> cursos;

	@ManyToMany(mappedBy = "disciplinas")
	private List<Professor> professores;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Short getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Short periodo) {
		this.periodo = periodo;
	}

	public Short getCargaHoraria() {
		return this.cargaHoraria;
	}

	public void setCargaHoraria(Short cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public List<Curso> getCursos() {
		return this.cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

}
