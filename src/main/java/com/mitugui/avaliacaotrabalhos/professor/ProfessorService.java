package com.mitugui.avaliacaotrabalhos.professor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mitugui.avaliacaotrabalhos.FabricaDeConexoes;

public class ProfessorService {
    public boolean cadastrarProfessor(DadosProfessorCadastro professor){
        Connection conn = FabricaDeConexoes.getConnection();

        String mensagem = "";
        
        if (professor.email() == null || professor.email().isBlank()) {
            mensagem +="O email não pode estar vazio.\n";
        }
        if (professor.senha() == null || professor.senha().isBlank()) {
            mensagem +="O senha não pode estar vazio.\n";
        }
        if (professor.siape() == null || professor.siape().toString().isBlank()) {
            mensagem +="A senha não pode estar vazia.\n";
        }

        if (!mensagem.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }

        return new ProfessorDAO(conn).salvar(professor);
    }
    
    public List<DadosProfessorListagem> listar() {
        try (Connection conn = FabricaDeConexoes.getConnection()) {
            return new ProfessorDAO(conn).listar();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores.", e);
        }
    }
}
