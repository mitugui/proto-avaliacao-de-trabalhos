package com.mitugui.avaliacaotrabalhos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.mitugui.avaliacaotrabalhos.modulos.estudante.DadosCadastroEstudante;
import com.mitugui.avaliacaotrabalhos.modulos.estudante.DadosListagemEstudante;

public interface EstudanteDAO {
    boolean salvar(DadosCadastroEstudante estudante, Integer usuario_id) throws SQLException;

    List<DadosListagemEstudante> listar() throws SQLException;
}
