package com.mitugui.avaliacaotrabalhos.modulos.usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.mitugui.avaliacaotrabalhos.config.FabricaDeConexoes;
import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;
import com.mitugui.avaliacaotrabalhos.exceptions.UsuarioNaoEncontradoException;
import com.mitugui.avaliacaotrabalhos.interfaces.UsuarioDAO;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean cadastrarUsuario(DadosCadastroUsuario usuario) {
        String mensagem = validarDadosCadastro(usuario);

        if (!mensagem.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }

        try (Connection conn = FabricaDeConexoes.getConnection()) {
            return usuarioDAO.salvar(conn, usuario);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário no banco de dados.", e);
        }
    }

    private String validarDadosCadastro(DadosCadastroUsuario usuario) {
        String mensagem = "";

        if (usuario.nome() == null || usuario.nome().isBlank()) {
            mensagem +="O nome não pode estar vazio.\n";
        }
        if (usuario.email() == null || usuario.email().isBlank()) {
            mensagem +="O email não pode estar vazio.\n";
        }
        if (usuario.senha() == null || usuario.senha().isBlank()) {
            mensagem +="A senha não pode estar vazia.\n";
        }

        return mensagem;
    }

    public List<DadosListagemUsuario> listar() {
        try (Connection conn = FabricaDeConexoes.getConnection()) {
            return usuarioDAO.listar(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco ao listar usuários", e);
        }
    }

    public boolean atualizar(DadosAtualizarUsuario usuario, DadosValidacaoUsuario usuarioValidacao){
        try (Connection conn = FabricaDeConexoes.getConnection()) {
            return usuarioDAO.atualizar(conn, usuario, validar(usuarioValidacao, conn));
        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco ao atualizar usuário", e);
        }
    }

    public boolean deletar(DadosValidacaoUsuario dados) {
        try (Connection conn = FabricaDeConexoes.getConnection()) {
            return usuarioDAO.deletar(conn, validar(dados, conn));
        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco ao deletar usuário", e);
        }
    }

    public Integer validar(DadosValidacaoUsuario dados, Connection conn) {
        try {
            return usuarioDAO.validar(conn, dados);
        } catch (UsuarioNaoEncontradoException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ConexaoBancoException e) {
            throw new RuntimeException("Erro inesperado ao validar usuário. " + e.getMessage());
        }
    }
}
