package com.mitugui.avaliacaotrabalhos.usuario;

import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;
import com.mitugui.avaliacaotrabalhos.exceptions.UsuarioNaoEncontradoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<DadosListagemUsuario> listar() throws SQLException {
        String sql = "SELECT u.nome, u.email FROM usuario u WHERE ativo = 1;";

        List<DadosListagemUsuario> usuarios = new ArrayList<>();

        try (
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");

                usuarios.add(new DadosListagemUsuario(nome, email));
            }
        }

        return usuarios;
    }

    public boolean deletar(Integer id) throws SQLException {
        String sql = "UPDATE usuario SET ativo = false WHERE id = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);

            return pstm.executeUpdate() == 1;
        }
    }

    public Integer validar(DadosValidacaoUsuario usuario) throws UsuarioNaoEncontradoException {
        String sql = "SELECT id FROM usuario WHERE ativo = 1 AND email = ? AND senha = ?;";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, usuario.email());
            pstm.setString(2, usuario.senha());

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new UsuarioNaoEncontradoException("O usuário não foi encontrado!!");
                }
            } catch (UsuarioNaoEncontradoException e) {
                throw new UsuarioNaoEncontradoException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new ConexaoBancoException("Erro na conexão com o banco de dados ao validar professor.", e);
        }
    }
}
