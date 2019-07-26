package br.com.mateusalxd.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mateusalxd.produto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
