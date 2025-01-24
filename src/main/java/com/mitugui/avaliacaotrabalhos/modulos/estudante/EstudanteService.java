package com.mitugui.avaliacaotrabalhos.modulos.estudante;

import com.mitugui.avaliacaotrabalhos.config.FabricaDeConexoes;
import com.mitugui.avaliacaotrabalhos.exceptions.ConexaoBancoException;
import com.mitugui.avaliacaotrabalhos.exceptions.UsuarioNaoEncontradoException;
import com.mitugui.avaliacaotrabalhos.interfaces.EstudanteDAO;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.DadosValidacaoUsuario;
import com.mitugui.avaliacaotrabalhos.modulos.usuario.JDBCUsuarioDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EstudanteService {
    private final EstudanteDAO estudanteDAO;

    public EstudanteService(EstudanteDAO estudanteDAO) {
        this.estudanteDAO = estudanteDAO;
    }

    public boolean cadastrarEstudante(DadosCadastroEstudante estudante){
        String mensagem = validarDadosCadastro(estudante);

        if(!mensagem.isBlank()){
            throw new IllegalArgumentException(mensagem);
        }
        
        DadosValidacaoUsuario usuario = new DadosValidacaoUsuario(estudante.email(), estudante.senha());

        try(Connection conn = FabricaDeConexoes.getConnection()){
            return estudanteDAO.salvar(conn, estudante, new JDBCUsuarioDAO().validar(conn, usuario));
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

    public List<DadosListagemEstudante> listar() {
        try (Connection conn = FabricaDeConexoes.getConnection()) {
            return estudanteDAO.listar(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Erro no banco ao listar estudantes.");
        }
    }
}
