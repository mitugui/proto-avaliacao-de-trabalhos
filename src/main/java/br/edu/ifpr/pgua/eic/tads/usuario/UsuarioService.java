package br.edu.ifpr.pgua.eic.tads.usuario;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public boolean cadastrarUsuario(DadosUsuarioCadastro usuario) {
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

        return usuarioDAO.salvar(usuario);
    }
}
