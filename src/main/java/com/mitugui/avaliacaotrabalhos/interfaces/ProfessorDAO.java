package com.mitugui.avaliacaotrabalhos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.mitugui.avaliacaotrabalhos.modulos.professor.DadosAtualizarProfessor;
import com.mitugui.avaliacaotrabalhos.modulos.professor.DadosCadastroProfessor;
import com.mitugui.avaliacaotrabalhos.modulos.professor.DadosListagemProfessor;

public interface ProfessorDAO {
    boolean salvar(DadosCadastroProfessor professor) throws SQLException;

    List<DadosListagemProfessor> listar() throws SQLException;

    boolean atualizar (DadosAtualizarProfessor dados) throws SQLException;
}
