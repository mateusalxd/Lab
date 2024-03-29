package br.com.mateusalxd.unilab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateusalxd.unilab.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	Optional<Perfil> findByNome(String nome);

}
