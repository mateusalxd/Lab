package br.com.mateusalxd.produto.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;

import br.com.mateusalxd.produto.model.Produto;

public class ProdutoDAO {

	private Properties getPropriedades() throws IOException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("banco.properties"));
		return propriedades;
	}

	public Produto inserir(Produto produto) throws IOException, SQLException, ClassNotFoundException {
		Properties propriedades = getPropriedades();
		String host = propriedades.getProperty("host");
		String port = propriedades.getProperty("port");
		String database = propriedades.getProperty("database");
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
		String user = propriedades.getProperty("user");
		String pass = propriedades.getProperty("pass");
		String dml = "insert into tbl_produto (ds_produto, vl_produto) values (?, ?)";
		produto.setId(-1);
		
		DriverManager.registerDriver(new Driver());

		try (Connection conexao = DriverManager.getConnection(url, user, pass);
				PreparedStatement comando = conexao.prepareStatement(dml, Statement.RETURN_GENERATED_KEYS);) {

			comando.setString(1, produto.getDescricao());
			comando.setDouble(2, produto.getValor());

			if (comando.executeUpdate() != 0) {
				ResultSet resultado = comando.getGeneratedKeys();
				if (resultado.next()) {
					produto.setId(resultado.getInt(1));
				}
			}
		}

		return produto;
	}

	public Produto buscar(int id) throws IOException, SQLException, ClassNotFoundException {
		Properties propriedades = getPropriedades();
		String host = propriedades.getProperty("host");
		String port = propriedades.getProperty("port");
		String database = propriedades.getProperty("database");
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
		String user = propriedades.getProperty("user");
		String pass = propriedades.getProperty("pass");
		String dql = "select id_produto, ds_produto, vl_produto from tbl_produto where id_produto = ?";
		Produto produto = new Produto();

		DriverManager.registerDriver(new Driver());

		try (Connection conexao = DriverManager.getConnection(url, user, pass);
				PreparedStatement comando = conexao.prepareStatement(dql);) {

			comando.setInt(1, id);
			ResultSet resultado = comando.executeQuery();
			if (resultado.next()) {
				produto.setId(resultado.getInt("id_produto"));
				produto.setDescricao(resultado.getString("ds_produto"));
				produto.setValor(resultado.getDouble("vl_produto"));
			}
		}

		return produto;
	}

}
