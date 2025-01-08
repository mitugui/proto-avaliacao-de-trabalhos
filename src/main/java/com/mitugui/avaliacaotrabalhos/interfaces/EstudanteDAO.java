package com.mitugui.avaliacaotrabalhos.interfaces;

import java.sql.SQLException;

import com.mitugui.avaliacaotrabalhos.modulos.estudante.DadosCadastroEstudante;

public interface EstudanteDAO {
    boolean salvar(DadosCadastroEstudante estudante, Integer usuario_id) throws SQLException;
}
