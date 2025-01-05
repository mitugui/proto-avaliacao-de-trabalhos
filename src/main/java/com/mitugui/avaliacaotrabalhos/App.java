package com.mitugui.avaliacaotrabalhos;

import com.mitugui.avaliacaotrabalhos.professor.DadosCadastroProfessor;
import com.mitugui.avaliacaotrabalhos.professor.DadosListagemProfessor;
import com.mitugui.avaliacaotrabalhos.professor.ProfessorService;
import com.mitugui.avaliacaotrabalhos.usuario.DadosCadastroUsuario;
import com.mitugui.avaliacaotrabalhos.usuario.DadosListagemUsuario;
import com.mitugui.avaliacaotrabalhos.usuario.UsuarioService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    private final static Scanner leitura = new Scanner(System.in);
    private final static UsuarioService usuarioService = new UsuarioService();
    private final static ProfessorService professorService = new ProfessorService();

    public static void main(String[] args) {
        int opcao = 1;

        while (opcao != 0) {
            mostrarMenu();

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    cadastrarProfessor();
                    break;
                case 4:
                    listarProfessores();
                    break;
                
                case 0:
                    System.out.println("Saindo...");
                    break;
                    
                default:
                    System.out.println("Digite uma opção válida!!");
                    break;
            }
        }
        leitura.close();
    }

    private static void mostrarMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("Escolha uma das seguintes opções do menu:");
        System.out.println();
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Listar usuários");
        System.out.println();
        System.out.println("3 - Cadastrar professor");
        System.out.println("4 - Listar professores");
        System.out.println();
        System.out.println("0 - Sair");
        System.out.println();
    }

    private static void cadastrarUsuario() {
        System.out.println("Digite os seguintes dados para o cadastro de um usuário");
        System.out.print("Nome: ");
        var nome = leitura.nextLine();

        System.out.print("Email: ");
        var email = leitura.nextLine();

        System.out.print("Senha: ");
        var senha = leitura.nextLine();
        
        try {
            if (usuarioService.cadastrarUsuario(new DadosCadastroUsuario(nome, email, senha))) {
                System.out.println("\nUsuário cadastrado com sucesso!!");
            } else {
                System.out.println("\nErrooooo!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n- " + e.getMessage());
        }
    }

    private static void listarUsuarios() {
        try {
            List<DadosListagemUsuario> usuarios = usuarioService.listar();

            if (!usuarios.isEmpty()) {
                usuarios.forEach(u -> {
                    System.out.println("\nNome: " + u.nome());
                    System.out.println("Email: " + u.email());
                });
            } else {
                System.out.println("Não hpa usuários cadastrados no momento!");
            }
        } catch (RuntimeException e) {
            System.out.println("\n- " + e.getMessage());
        }
    }

    private static void cadastrarProfessor() {
        System.out.println("Digite os seguintes dados para o cadastro de um professor");
        
        System.out.print("Email: ");
        var email = leitura.nextLine();
        
        System.out.print("Senha: ");
        var senha = leitura.nextLine();

        System.out.print("Siape: ");
        var siape = leitura.nextInt();

        try {
            if (professorService.cadastrarProfessor(new DadosCadastroProfessor(email, senha, siape))) {
                System.out.println("\nProfessor cadastrado com sucesso!!");
            } else {
                System.out.println("\nErrooooo!");
            }
        } catch (RuntimeException e) {
            System.out.println("\n- " + e.getMessage());
        }
    }

    private static void listarProfessores() {
        List<DadosListagemProfessor> professores = professorService.listar();

        try {
            if (!professores.isEmpty()) {
                professores.forEach(p -> {
                    System.out.println("\nNome: " + p.nome());
                    System.out.println("Email: " + p.email());
                    System.out.println("Siape: " + p.siape());
                });
            } else {
                System.out.println("Não há professores cadastrados no momento!");
            }
        } catch (RuntimeException e) {
            System.out.println("\n- " + e.getMessage());
        }
    }
}
