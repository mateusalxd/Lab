package br.com.mateusalxd.unilab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateusalxd.unilab.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	Optional<Professor> findByCodigo(String codigo);

}
