package com.mitugui.avaliacaotrabalhos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mitugui.avaliacaotrabalhos.modulos.professor.DadosAtualizarProfessor;
import com.mitugui.avaliacaotrabalhos.modulos.professor.DadosCadastroProfessor;
import com.mitugui.avaliacaotrabalhos.modulos.professor.DadosListagemProfessor;

public interface ProfessorDAO {
    boolean salvar(Connection conn, DadosCadastroProfessor professor) throws SQLException;

    List<DadosListagemProfessor> listar(Connection conn) throws SQLException;

    boolean atualizar (Connection conn, DadosAtualizarProfessor dados) throws SQLException;
}
