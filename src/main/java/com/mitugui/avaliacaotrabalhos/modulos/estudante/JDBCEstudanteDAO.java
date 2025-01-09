package com.mitugui.avaliacaotrabalhos.modulos.estudante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;
import com.mitugui.avaliacaotrabalhos.interfaces.EstudanteDAO;

public class JDBCEstudanteDAO implements EstudanteDAO {
    private Connection conn;

    public JDBCEstudanteDAO(Connection connection){
        this.conn = connection;
    }

    @Override
    public boolean salvar(DadosCadastroEstudante estudante, Integer usuario_id) throws SQLException {
        String sqlVerificacao = "SELECT 1 FROM professor WHERE usuario_id = ?";

        try(PreparedStatement pstmVerificacao = conn.prepareStatement(sqlVerificacao)) {
            pstmVerificacao.setInt(1, usuario_id);

            try (ResultSet rs = pstmVerificacao.executeQuery()) {
                if (!rs.next()) {
                    String sqlCadastro = "INSERT INTO estudante(usuario_id, curso_id, ano_ingresso, matricula) VALUES (?, ?, ?, ?);";

                    try (PreparedStatement pstmCadastro = conn.prepareStatement(sqlCadastro)) {
                        pstmCadastro.setInt(1, usuario_id);
                        pstmCadastro.setInt(2, 1);
                        pstmCadastro.setInt(3, estudante.anoIngresso());
                        pstmCadastro.setString(4, estudante.matricula());
                        
                        return pstmCadastro.executeUpdate() == 1;  
                    }
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new ConexaoBancoException("Erro na conexão com o banco de dados ao cadastrar estudante.", e);
        }
    }
}
