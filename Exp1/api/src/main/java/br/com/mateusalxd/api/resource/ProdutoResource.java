package br.com.mateusalxd.api.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.mateusalxd.api.dao.ProdutoDAO;
import br.com.mateusalxd.api.model.Produto;

@Path("produtos")
public class ProdutoResource {

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Produto buscarProduto(@PathParam("id") int id) {
		return new ProdutoDAO().buscar(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
	public Response inserirProduto(Produto produto) {
		Produto produtoNovo = new ProdutoDAO().inserir(produto);
		if (produtoNovo.getId() == -1) {
			return Response.status(406).build();
		} else {
			URI uri = URI.create("/produtos/" + produto.getId());
			return Response.created(uri).build();
		}
	}

}
