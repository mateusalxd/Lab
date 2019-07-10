package br.com.mateusalxd.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.mateusalxd.api.model.Produto;

public class ProdutoDAO {

	public Produto inserir(Produto produto) {
		Connection conexao = null;
		produto.setId(-1);

		try {
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:32773/Exp1", "root", "1234");

			PreparedStatement comando = conexao.prepareStatement(
					"insert into tbl_produto (ds_produto, vl_produto) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
			comando.setString(1, produto.getDescricao());
			comando.setDouble(2, produto.getValor());

			if (comando.executeUpdate() != 0) {
				ResultSet resultado = comando.getGeneratedKeys();
				if (resultado.next()) {
					int id = resultado.getInt(1);
					produto.setId(id);
					comando.close();
					conexao.close();
					return produto;
				} else {
					return produto;
				}
			} else {
				comando.close();
				conexao.close();
				return produto;
			}
		} catch (Exception e) {
			return produto;
		}
	}

	public Produto buscar(int id) {
		Connection conexao = null;
		Produto produto = new Produto();

		try {
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:32773/Exp1", "root", "1234");

			PreparedStatement comando = conexao.prepareStatement(
					"select id_produto, ds_produto, vl_produto from tbl_produto where id_produto = ?");
			comando.setInt(1, id);

			ResultSet resultado = comando.executeQuery();
			if (resultado.next()) {
				produto.setId(resultado.getInt("id_produto"));
				produto.setDescricao(resultado.getString("ds_produto"));
				produto.setValor(resultado.getDouble("vl_produto"));
				comando.close();
				conexao.close();
				return produto;
			} else {
				return produto;
			}

		} catch (Exception e) {
			return produto;
		}
	}

}
