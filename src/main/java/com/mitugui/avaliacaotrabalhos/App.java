package com.mitugui.avaliacaotrabalhos;

import java.util.Scanner;

import com.mitugui.avaliacaotrabalhos.professor.DadosProfessorCadastro;
import com.mitugui.avaliacaotrabalhos.professor.ProfessorService;
import com.mitugui.avaliacaotrabalhos.usuario.DadosUsuarioCadastro;
import com.mitugui.avaliacaotrabalhos.usuario.UsuarioService;

public class App {
    private static Scanner leitura = new Scanner(System.in);
    private static UsuarioService usuarioService = new UsuarioService();
    private static ProfessorService professorService = new ProfessorService();

    public static void main(String[] args) {
        int opcao = 1;

        while (opcao != 0) {      
            System.out.println("-----------------------------------------");  
            System.out.println("Escolha uma das seguintes opções do menu:\n");

            mostrarMenu();

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    cadastrarProfessor();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                    
                default:
                    System.out.println("Digite uma opção válida!!");
                    break;
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Cadastrar professor");
        System.out.println("0 - Sair");
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
            if (usuarioService.cadastrarUsuario(new DadosUsuarioCadastro(nome, email, senha))) {
                System.out.println("\nUsuário cadastrado com sucesso!!");
            } else {
                System.out.println("\nErrooooo!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n- " + e.getMessage());
        }
    }

    private static void cadastrarProfessor(){
        System.out.println("Digite os seguintes dados para o cadastro de um professor");
        
        System.out.print("Email: ");
        var email = leitura.nextLine();
        
        System.out.print("Senha: ");
        var senha = leitura.nextLine();

        System.out.print("Siape: ");
        var siape = leitura.nextInt();

        try {
            if (professorService.cadastrarProfessor(new DadosProfessorCadastro(email, senha, siape))) {
                System.out.println("\nProfessor cadastrado com sucesso!!");
            } else {
                System.out.println("\nErrooooo!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n- " + e.getMessage());
        }
    }
}
