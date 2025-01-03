package com.mitugui.avaliacaotrabalhos;

import java.util.Scanner;

import com.mitugui.avaliacaotrabalhos.usuario.DadosUsuarioCadastro;
import com.mitugui.avaliacaotrabalhos.usuario.UsuarioService;

public class App {
    private static Scanner leitura = new Scanner(System.in);
    private static UsuarioService usuarioService = new UsuarioService();
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
        System.out.println("0 - Sair");
    }

    private static void cadastrarUsuario() {
        System.out.println("Digite os seguintes dados para o cadastro de um usuário");
        System.out.println("Nome: ");
        var nome = leitura.nextLine();

        System.out.println("Email: ");
        var email = leitura.nextLine();

        System.out.println("Senha: ");
        var senha = leitura.nextLine();
        
        try {
            if (usuarioService.cadastrarUsuario(new DadosUsuarioCadastro(nome, email, senha))) {
                System.out.println("Usuário cadastrado com sucesso!!");
            } else {
                System.out.println("Errooooo!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("- " + e.getMessage());
        }
    }
}
