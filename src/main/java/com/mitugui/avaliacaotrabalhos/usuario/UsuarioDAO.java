package com.mitugui.avaliacaotrabalhos.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection connection) {
        this.conn = connection;
    }

    public boolean salvar(DadosUsuarioCadastro usuario) {
        PreparedStatement pstm = null;

        try {
            String sql = "INSERT INTO usuario(nome, email, senha) VALUES (?, ?, ?)";

            pstm = conn.prepareStatement(sql);

            pstm.setString(1, usuario.nome());
            pstm.setString(2, usuario.email());
            pstm.setString(3, usuario.senha());

            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário no banco de dados.", e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());                
            }
        }
    }
}
