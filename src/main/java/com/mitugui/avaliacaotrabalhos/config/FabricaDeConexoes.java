package com.mitugui.avaliacaotrabalhos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;

import io.github.cdimascio.dotenv.Dotenv;

public class FabricaDeConexoes {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String DB_TYPE = dotenv.get("DB_TYPE");
    private static final String DB_HOST = dotenv.get("DB_HOST");
    private static final String DB_PORT = dotenv.get("DB_PORT");
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private static final String DB_URL = "jdbc:" + DB_TYPE + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private FabricaDeConexoes() {
    }

    public static Connection getConnection() throws RuntimeException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new ConexaoBancoException("Falha em conectar com o banco", e);
        }
    }
}
