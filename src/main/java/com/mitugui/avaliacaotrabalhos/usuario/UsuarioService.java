package com.mitugui.avaliacaotrabalhos.usuario;

import java.sql.Connection;

import com.mitugui.avaliacaotrabalhos.FabricaDeConexoes;

public class UsuarioService {
    public boolean cadastrarUsuario(DadosUsuarioCadastro usuario) {
        Connection conn = FabricaDeConexoes.getConnection();

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

        if (!mensagem.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }

        return new UsuarioDAO(conn).salvar(usuario);
    }
}
