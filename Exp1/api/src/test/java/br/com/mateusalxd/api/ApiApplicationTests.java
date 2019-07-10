package br.com.mateusalxd.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.mateusalxd.api.dao.ProdutoDAO;
import br.com.mateusalxd.api.model.Produto;

public class ApiApplicationTests {

	private HttpServer server;

	@Before
	public void iniciar() {
		server = Servidor.inicializaServidor();
	}

	@After
	public void parar() {
		server.stop();
	}

	@Test
	public void testaInsertPeloProdutoDAO() {

		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.inserir(new Produto("teste", 10));
		Assert.assertTrue(produto.getId() != -1);

	}

	@Test
	public void testaSelectProdutoPeloProdutoDAO() {
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.buscar(23);
		System.out.println("Id: " + String.valueOf(produto.getId()));
		Assert.assertTrue(produto.getId() == 23);
	}

	@Test
	public void testaInsertPeloServico() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("http://localhost:8080");

		Produto produto = new Produto("Maçã", 2.5);
		Entity<Produto> entity = Entity.entity(produto, MediaType.APPLICATION_XML + ";charset=utf-8");

		Response resposta = target.path("/produtos").request().post(entity);
		Assert.assertEquals(resposta.getStatus(), 201);

		String recursoProdutoNovo = resposta.getHeaderString("Location");

		Produto produtoNovo = target.path(recursoProdutoNovo).request().get(Produto.class);
		Assert.assertEquals(produtoNovo.getDescricao(), "Maçã");

	}

}
