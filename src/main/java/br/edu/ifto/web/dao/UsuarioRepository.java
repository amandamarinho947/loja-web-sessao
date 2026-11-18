package br.edu.ifto.web.dao;

import br.edu.ifto.web.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
    public Usuario autenticar(String email, String senha) throws SQLException {
        if (email == null || senha == null) {
            return null;
        }

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT nome, email, senha FROM usuarios WHERE LOWER(email) = LOWER(?)")) {
            statement.setString(1, email.trim());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next() && result.getString("senha").equals(senha)) {
                    return new Usuario(
                            result.getString("nome"),
                            result.getString("email"),
                            result.getString("senha"));
                }
            }
        }
        return null;
    }
}
