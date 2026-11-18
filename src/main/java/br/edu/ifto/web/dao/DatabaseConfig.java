package br.edu.ifto.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {
    public static final String URL = System.getenv().getOrDefault(
            "JDBC_URL", "jdbc:h2:file:./target/loja-web-db");
    public static final String USER = System.getenv().getOrDefault("JDBC_USER", "sa");
    public static final String PASSWORD = System.getenv().getOrDefault("JDBC_PASSWORD", "");

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
