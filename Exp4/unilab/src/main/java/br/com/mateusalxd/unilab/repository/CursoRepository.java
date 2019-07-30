package br.com.mateusalxd.unilab.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateusalxd.unilab.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
