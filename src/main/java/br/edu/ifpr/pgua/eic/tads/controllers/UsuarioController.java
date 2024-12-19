package br.edu.ifpr.pgua.eic.tads.controllers;

import br.edu.ifpr.pgua.eic.tads.usuario.DadosUsuarioCadastro;
import br.edu.ifpr.pgua.eic.tads.usuario.UsuarioService;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class UsuarioController {
    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    public Handler get = (Context ctx)->{
        ctx.render("cadastro.html");
    };

    public Handler post = (Context ctx)->{
        try {
            String nome = ctx.formParam("nome");
            String email = ctx.formParam("email");
            String senha = ctx.formParam("senha");
    
            DadosUsuarioCadastro usuario = new DadosUsuarioCadastro(nome, email, senha);
    
            boolean sucesso = service.cadastrarUsuario(usuario);
            if(sucesso) {
                ctx.status(201).result("Usuário cadastrado com sucesso!");
            } else {
                ctx.status(500).result("Erro ao cadastrar o usuário.");
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erro inesperado: " + e.getMessage());
        }
    };
}
