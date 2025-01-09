package com.mitugui.avaliacaotrabalhos.modulos.professor;

import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;
import com.mitugui.avaliacaotrabalhos.exceptions.UsuarioNaoEncontradoException;
import com.mitugui.avaliacaotrabalhos.interfaces.ProfessorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCProfessorDAO implements ProfessorDAO {
    private final Connection conn;

    public JDBCProfessorDAO(Connection connection){
        this.conn = connection;
    }

    private int procurar(DadosCadastroProfessor professor) throws UsuarioNaoEncontradoException {
        String sql = "SELECT id FROM usuario WHERE email = ? AND senha = ? AND ativo = 1;";
        int usuario_id = 0;

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, professor.email());
            pstm.setString(2, professor.senha());

            try (ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    usuario_id = rs.getInt("id");
                } else {
                    throw new UsuarioNaoEncontradoException("Usuario não encontrado no banco de dados!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao procurar usuário no banco de dados.", e);
        }

        return usuario_id;
    }

    @Override
    public boolean salvar(DadosCadastroProfessor professor) throws SQLException {
        int usuarioId;

        try {
            usuarioId = procurar(professor);
        } catch (UsuarioNaoEncontradoException e) {
            throw new IllegalArgumentException("Não foi possível salvar: " + e.getMessage(), e);
        }

        String sqlVerificacao = "SELECT 1 FROM estudante WHERE usuario_id = ?";

        try (PreparedStatement pstmVerificacao = conn.prepareStatement(sqlVerificacao)) {
            pstmVerificacao.setInt(1, usuarioId);

            try (ResultSet rs = pstmVerificacao.executeQuery()) {
                if (!rs.next()) {
                    String sqlCadastro = "INSERT INTO professor(usuario_id, siape) VALUES (?, ?)";

                    try (PreparedStatement pstmCadastro = conn.prepareStatement(sqlCadastro)) {
                        pstmCadastro.setInt(1, usuarioId);
                        pstmCadastro.setInt(2, professor.siape());

                        return pstmCadastro.executeUpdate() == 1;
                    }
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new ConexaoBancoException("Erro na conexão com o banco de dados ao cadastrar professor.", e);
        }
    }

    @Override
    public List<DadosListagemProfessor> listar() throws SQLException {
        String sql = "SELECT u.nome, u.email, p.siape FROM professor p JOIN usuario u ON p.usuario_id = u.id WHERE ativo = 1;";

        List<DadosListagemProfessor> professores = new ArrayList<>();
        
        try (
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()
            ) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                Integer siape = rs.getInt("siape");

                professores.add(new DadosListagemProfessor(nome, email, siape));
            }
        }

        return professores;
    }

    @Override
    public boolean atualizar (DadosAtualizarProfessor dados) throws SQLException {
        String sql = "UPDATE professor p JOIN usuario u ON p.usuario_id = u.id SET siape = ? WHERE ativo = 1 AND email = ? AND senha = ?;";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, dados.siape());
            pstm.setString(2, dados.email());
            pstm.setString(3, dados.senha());

            return pstm.executeUpdate() == 1;
        }
    }
}
