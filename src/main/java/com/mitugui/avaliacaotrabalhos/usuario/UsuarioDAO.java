package com.mitugui.avaliacaotrabalhos.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection connection) {
        this.conn = connection;
    }

    public boolean salvar(DadosCadastroUsuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario(nome, email, senha) VALUES (?, ?, ?)";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, usuario.nome());
            pstm.setString(2, usuario.email());
            pstm.setString(3, usuario.senha());

            return pstm.executeUpdate() == 1;
        }
    }
}
