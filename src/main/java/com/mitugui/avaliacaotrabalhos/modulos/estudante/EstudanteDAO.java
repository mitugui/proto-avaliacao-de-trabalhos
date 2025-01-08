package com.mitugui.avaliacaotrabalhos.modulos.estudante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;

public class EstudanteDAO {
    private Connection conn;

    public EstudanteDAO(Connection connection){
        this.conn = connection;
    }

    public boolean salvar(DadosCadastroEstudante estudante, Integer usuario_id) throws SQLException {
        String sql = "INSERT INTO estudante(usuario_id, curso_id, ano_ingresso, matricula) VALUES (?, ?, ?, ?);";

        try(PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setInt(1, usuario_id);
            pstm.setInt(2, 1);
            pstm.setInt(3, estudante.anoIngresso());
            pstm.setString(4, estudante.matricula());

            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new ConexaoBancoException("Erro na conexão com o banco de dados ao cadastrar estudante.", e);
        }
    }
}
