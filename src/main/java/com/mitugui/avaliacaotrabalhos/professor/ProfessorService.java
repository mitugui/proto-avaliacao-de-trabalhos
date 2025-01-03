package com.mitugui.avaliacaotrabalhos.professor;

import java.sql.Connection;

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
}
