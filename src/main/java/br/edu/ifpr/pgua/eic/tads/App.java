package br.edu.ifpr.pgua.eic.tads;

import br.edu.ifpr.pgua.eic.tads.controllers.IndexController;
import br.edu.ifpr.pgua.eic.tads.controllers.UsuarioController;
import br.edu.ifpr.pgua.eic.tads.usuario.UsuarioDAO;
import br.edu.ifpr.pgua.eic.tads.usuario.UsuarioService;
import br.edu.ifpr.pgua.eic.tads.utils.JavalinUtils;

public class App {
    public static void main( String[] args ){
        var app = JavalinUtils.makeApp(7070);
        
        IndexController indexController = new IndexController();
        UsuarioController usuarioController = new UsuarioController(new UsuarioService(new UsuarioDAO(FabricaDeConexoes.getConnection())));
        
        app.get("/", indexController.get);

        app.get("/cadastro", usuarioController.get);
        app.post("/cadastro/usuario", usuarioController.post);
    }
}
