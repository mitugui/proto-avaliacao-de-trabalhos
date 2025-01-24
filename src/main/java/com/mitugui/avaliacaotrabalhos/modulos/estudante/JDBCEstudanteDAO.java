package com.mitugui.avaliacaotrabalhos.modulos.estudante;

import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;
import com.mitugui.avaliacaotrabalhos.interfaces.EstudanteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCEstudanteDAO implements EstudanteDAO {
    @Override
    public boolean salvar(Connection conn, DadosCadastroEstudante estudante, Integer usuario_id) throws SQLException {
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

    @Override
    public List<DadosListagemEstudante> listar(Connection conn) throws SQLException {
        String sql = "SELECT u.nome, u.email, e.matricula, c.nome as curso, e.ano_ingresso FROM estudante e " +
                        "JOIN usuario u ON e.usuario_id = u.id " +
                        "JOIN curso c ON e.curso_id = c.id " +
                        "WHERE u.ativo = 1;";

        List<DadosListagemEstudante> estudantes = new ArrayList<>();

        try (
                PreparedStatement pstm = conn.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery()
        ) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String matricula = rs.getString("matricula");
                String curso = rs.getString("curso");
                Integer anoIngresso = rs.getInt("ano_ingresso");

                estudantes.add(new DadosListagemEstudante(nome, email, matricula, curso, anoIngresso));
            }
        }

        return estudantes;
    }
}
