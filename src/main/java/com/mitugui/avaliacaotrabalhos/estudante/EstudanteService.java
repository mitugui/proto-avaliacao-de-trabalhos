package com.mitugui.avaliacaotrabalhos.estudante;

import java.sql.Connection;
import java.sql.SQLException;

import com.mitugui.avaliacaotrabalhos.FabricaDeConexoes;
import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;
import com.mitugui.avaliacaotrabalhos.exceptions.UsuarioNaoEncontradoException;
import com.mitugui.avaliacaotrabalhos.usuario.DadosValidacaoUsuario;
import com.mitugui.avaliacaotrabalhos.usuario.UsuarioDAO;

public class EstudanteService {
    public boolean cadastrarEstudante(DadosCadastroEstudante estudante){
        String mensagem = validarDadosCadastro(estudante);

        if(!mensagem.isBlank()){
            throw new IllegalArgumentException(mensagem);
        }
        
        DadosValidacaoUsuario usuario = new DadosValidacaoUsuario(estudante.email(), estudante.senha());

        try(Connection conn = FabricaDeConexoes.getConnection()){
            return new EstudanteDAO(conn).salvar(estudante, new UsuarioDAO(conn).validar(usuario));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro na validação dos dados: " + e.getMessage());
        } catch (ConexaoBancoException e) {
            throw new RuntimeException("Erro inesperado ao cadastrar estudante. " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados ao cadastrar estudante.", e);
        } catch (UsuarioNaoEncontradoException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String validarDadosCadastro(DadosCadastroEstudante estudante) {
        String mensagem = "";

        if (estudante.email() == null || estudante.email().isBlank()) {
            mensagem +="O email não pode estar vazio.\n";
        }
        if (estudante.senha() == null || estudante.senha().isBlank()) {
            mensagem +="O senha não pode estar vazio.\n";
        }
        if (estudante.anoIngresso() == null || estudante.anoIngresso().toString().isBlank()) {
            mensagem +="O ano de ingresso não pode estar vazia.\n";
        }
        if (estudante.matricula() == null || estudante.matricula().isBlank()) {
            mensagem +="A matrícula não pode estar vazio.\n";
        }

        return mensagem;
    }
}
