package com.mitugui.avaliacaotrabalhos.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mitugui.avaliacaotrabalhos.exceptions.UsuarioNaoEncontradoException;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosAtualizarUsuario;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosCadastroUsuario;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosListagemUsuario;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosValidacaoUsuario;

public interface UsuarioDAO {
    boolean salvar(Connection conn, DadosCadastroUsuario usuario) throws SQLException;
    
    List<DadosListagemUsuario> listar(Connection conn) throws SQLException;

    boolean atualizar(Connection conn,DadosAtualizarUsuario usuario, Integer id) throws SQLException;

    boolean deletar(Connection conn,Integer id) throws SQLException;

    Integer validar(Connection conn,DadosValidacaoUsuario usuario) throws UsuarioNaoEncontradoException;
}
