package br.com.mateusalxd.unilab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateusalxd.unilab.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNome(String nome);

}
