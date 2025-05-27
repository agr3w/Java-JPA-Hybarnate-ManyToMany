package app;

import java.util.Scanner;

import enumeration.OpcaoMenu;
import service.MatriculaService;

public class AppMatricula {

    public static void main(String[] args) {
        MatriculaService matriculaService = new MatriculaService();
        Scanner scanner = new Scanner(System.in);

        boolean sair = false;

        while (!sair) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scanner.nextLine());

            OpcaoMenu opcaoMenu = OpcaoMenu.obterPorCodigo(opcao);
            System.out.println("\n" + opcaoMenu.getDescricao());

            switch (opcaoMenu) {
                case CADASTRAR:
                    matriculaService.cadastrarAluno();
                    break;
                case EDITAR:
                    matriculaService.cadastrarCurso();
                    break;
                case REMOVER:
                    matriculaService.excluirAluno();
                    break;
                case LISTAR_ALUNOS:
                    matriculaService.listarAlunos();
                    break;
                case PESQUISAR_PELO_CPF:
                    matriculaService.matricularAluno();
                    break;
                case PESQUISAR_PELAS_INICIAIS:
                    matriculaService.cancelarMatricula();
                    break;
                case LISTAR_CURSOS:
                    matriculaService.listarCursosComAlunos();
                    break;
                case EXCLUIR_CURSO:
                    matriculaService.excluirCurso();
                    break;
                case SAIR:
                    sair = true;
                    System.out.println("Encerrando o sistema...");
                    break;
                case INVALIDA:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
                default:
                    break;
            }

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }

        matriculaService.close();
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n========== SISTEMA DE MATRÍCULAS ==========");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Cadastrar curso");
        System.out.println("3 - Matricular aluno no curso");
        System.out.println("4 - Cancelar matrícula do aluno no curso");
        System.out.println("5 - Excluir aluno");
        System.out.println("6 - Excluir curso");
        System.out.println("7 - Listar todos os alunos");
        System.out.println("8 - Listar todos os cursos e seus respectivos alunos");
        System.out.println("0 - Sair");
    }
}