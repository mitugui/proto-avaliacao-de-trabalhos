package com.mitugui.avaliacaotrabalhos.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.mitugui.avaliacaotrabalhos.exceptions.UsuarioNaoEncontradoException;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosAtualizarUsuario;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosCadastroUsuario;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosListagemUsuario;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosValidacaoUsuario;

public interface UsuarioDAO {
    boolean salvar(DadosCadastroUsuario usuario) throws SQLException;  
    
    public List<DadosListagemUsuario> listar() throws SQLException;

    boolean atualizar(DadosAtualizarUsuario usuario, Integer id) throws SQLException;

    boolean deletar(Integer id) throws SQLException;

    Integer validar(DadosValidacaoUsuario usuario) throws UsuarioNaoEncontradoException;
}
