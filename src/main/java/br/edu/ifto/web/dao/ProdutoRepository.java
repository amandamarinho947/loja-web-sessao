package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    public List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(
                     "SELECT id, nome, descricao, preco, icone FROM produtos ORDER BY id")) {
            while (result.next()) {
                produtos.add(mapear(result));
            }
        }
        return produtos;
    }

    public Produto buscarPorId(int id) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, nome, descricao, preco, icone FROM produtos WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapear(result) : null;
            }
        }
    }

    private Produto mapear(ResultSet result) throws SQLException {
        return new Produto(
                result.getInt("id"),
                result.getString("nome"),
                result.getString("descricao"),
                result.getBigDecimal("preco"),
                result.getString("icone"));
    }
}
