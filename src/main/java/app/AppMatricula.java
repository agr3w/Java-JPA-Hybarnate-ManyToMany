package app;

import java.util.List;
import entity.Aluno;
import enumeration.OpcaoMenu;
import service.AlunoService;
import service.CursoService;

import java.util.Scanner;

public class AppMatricula {

    private static AlunoService alunoService = new AlunoService();
    private static CursoService cursoService = new CursoService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        for (;;) {
            imprimirMenu();
            OpcaoMenu opcao = lerOpcaoMenu();
            switch (opcao) {
                case CADASTRAR_ALUNO -> cadastrarAluno();
                case CADASTRAR_CURSO -> cadastrarCurso();
                case REALIZAR_MATRICULA -> realizarMatricula();
                case CANCELAR_MATRICULA -> cancelarMatricula();
                case EXCLUIR_ALUNO -> excluirAluno();
                case EXCLUIR_CURSO -> excluirCurso();
                case LISTAR_ALUNOS -> listarAlunos();
                case LISTAR_CURSOS -> listarCursos();
                case SAIR -> {
                    System.out.println("Encerrando o sistema...");
                    fecharRecursos();
                    return;
                }
                default -> System.out.println("Atenção: Opção inválida!");
            }
            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }
    }

    private static void imprimirMenu() {
        System.out.println("=========================");
        System.out.println("MENU PRINCIPAL");
        System.out.println("=========================");
        for (OpcaoMenu opcao : OpcaoMenu.values()) {
            if (opcao != OpcaoMenu.OPCAO_INVALIDA) {
                System.out.println(opcao.getNome());
            }
        }
        System.out.println("=========================");
    }

    private static String leituraTecladoTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static long leituraTecladoNumerico(String mensagem) {
        long numeroDigitado = 0;
        try {
            System.out.print(mensagem);
            String valorDigitado = scanner.nextLine();
            numeroDigitado = Long.parseLong(valorDigitado);
            return numeroDigitado;
        } catch (Exception e) {
            System.out.println("Atenção: Você deve digitar um valor numérico!");
            numeroDigitado = leituraTecladoNumerico(mensagem);
        }
        return numeroDigitado;
    }

    private static OpcaoMenu lerOpcaoMenu() {
        long opcaoDigitada = leituraTecladoNumerico("Informe a opção desejada: ");
        return OpcaoMenu.obterPorCodigo(opcaoDigitada);
    }

    private static void cadastrarAluno() {
        System.out.println(OpcaoMenu.CADASTRAR_ALUNO.getDescricao());
        Aluno aluno = new Aluno();
        try {
            alunoService.cadastrar(aluno);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private static void cadastrarCurso() {
        System.out.println(OpcaoMenu.CADASTRAR_CURSO.getDescricao());
        cursoService.cadastrar();
    }

    private static void excluirAluno() {
        System.out.println(OpcaoMenu.EXCLUIR_ALUNO.getDescricao());
        alunoService.remover();
    }

    private static void excluirCurso() {
        System.out.println(OpcaoMenu.EXCLUIR_CURSO.getDescricao());
        cursoService.remover();
    }

    private static void listarAlunos() {
        List<Aluno> alunos = alunoService.listar();
        if (alunos == null || alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado na base de dados!");
        } else {
            System.out.println(String.format("Alunos encontrados: %d", alunos.size()));
            for (Aluno aluno : alunos) {
                System.out.println(String.format("#%d CPF: %s - Nome: %s", aluno.getId(), aluno.getCpf(), aluno.getNome()));
            }
        }
        System.out.println();
    }

    private static void listarCursos() {
        cursoService.listarComAlunos();
    }

    private static void realizarMatricula() {
        System.out.println(OpcaoMenu.REALIZAR_MATRICULA.getDescricao());
        alunoService.matricular();
    }

    private static void cancelarMatricula() {
        System.out.println(OpcaoMenu.CANCELAR_MATRICULA.getDescricao());
        alunoService.cancelarMatricula();
    }

    private static void fecharRecursos() {
        scanner.close();
    }
}