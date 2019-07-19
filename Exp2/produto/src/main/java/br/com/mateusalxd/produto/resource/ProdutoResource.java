package br.com.mateusalxd.produto.resource;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.mateusalxd.produto.dao.ProdutoDAO;
import br.com.mateusalxd.produto.model.Produto;

@Path("produtos")
public class ProdutoResource {

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Produto buscarProduto(@PathParam("id") int id) {
		Produto produto = new Produto();
		try {
			produto = new ProdutoDAO().buscar(id);
			return produto;
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return produto;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Response inserirProduto(Produto produto) {
		try {
			Produto produtoNovo = new ProdutoDAO().inserir(produto);
			if (produtoNovo.getId() == -1) {
				return Response.status(406).build();
			} else {
				URI uri = URI.create("/produtos/" + produto.getId());
				return Response.created(uri).build();
			}
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(406).build();
		}
	}

}
