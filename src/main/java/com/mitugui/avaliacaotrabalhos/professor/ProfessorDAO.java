package com.mitugui.avaliacaotrabalhos.professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {
    private Connection conn;

    public ProfessorDAO(Connection connection){
        this.conn = connection;
    }

    private int procurar(DadosProfessorCadastro professor){
        PreparedStatement pstm = null;
        int usuario_id = 0;

        try{
            String sql = "SELECT id FROM usuario WHERE email = ? AND senha = ? AND ativo = 1;";

            pstm = conn.prepareStatement(sql);

            pstm.setString(1, professor.email());
            pstm.setString(2, professor.senha());

            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                usuario_id = rs.getInt("id");
            }else{
                throw new IllegalArgumentException("Usuario não encontrado no banco de dados!");
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao procurar usuário no banco de dados.", e);
        }

        return usuario_id;
    }

    public boolean salvar(DadosProfessorCadastro professor){
        PreparedStatement pstm = null;
        int usuario_id = procurar(professor);

        try{
            String sql = "INSERT INTO professor(usuario_id, siape) VALUES (?, ?)";

            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, usuario_id);
            pstm.setInt(2, professor.siape());

            return pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar professor no banco de dados.", e);
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
