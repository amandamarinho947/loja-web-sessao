package br.edu.ifto.web.dao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class DatabaseInitializerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS produtos (
                        id INT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        descricao VARCHAR(255) NOT NULL,
                        preco DECIMAL(10,2) NOT NULL,
                        icone VARCHAR(20) NOT NULL
                    )
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS usuarios (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        senha VARCHAR(255) NOT NULL
                    )
                    """);
            statement.execute("""
                    MERGE INTO produtos (id, nome, descricao, preco, icone) KEY(id)
                    VALUES
                        (1, 'Notebook', 'Notebook para trabalho e estudos.', 3500.00, '💻'),
                        (2, 'Mouse', 'Mouse óptico USB.', 80.00, '🖱️'),
                        (3, 'Teclado Mecânico', 'Teclado mecânico para produtividade.', 250.00, '⌨️'),
                        (4, 'Monitor', 'Monitor para estação de trabalho.', 1200.00, '🖥️'),
                        (5, 'Headset', 'Headset para aulas e reuniões.', 180.00, '🎧')
                    """);
            statement.execute("""
                    MERGE INTO usuarios (nome, email, senha) KEY(email)
                    VALUES
                        ('Amanda', 'amanda@gmail.com', '123'),
                        ('Carlos', 'carlos@gmail.com', '123')
                    """);
        } catch (SQLException exception) {
            throw new IllegalStateException("Não foi possível inicializar o banco de dados.", exception);
        }
    }
}
