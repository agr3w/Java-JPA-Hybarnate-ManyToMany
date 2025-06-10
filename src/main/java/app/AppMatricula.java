package app;

import enumeration.OpcaoMenu;
import service.AlunoService;
import service.CursoService;
import service.MatriculaService;
import util.TecladoUtil;

public class AppMatricula {

    private static AlunoService alunoService = new AlunoService();
    private static CursoService cursoService = new CursoService();
    private static MatriculaService matriculaService = new MatriculaService();

    /**
     * Método principal que inicia o sistema de matrícula.
     * Ele exibe o menu e processa as opções selecionadas pelo usuário.
     */
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
                    return;
                }
                default -> System.out.println("Atenção: Opção inválida! Por favor, escolha uma opção válida.");
            }
            System.out.println("\nPressione Enter para continuar...");
            TecladoUtil.leitura(); // Agora usando o utilitário para aguardar o Enter
        }
    }

    /**
     * Método para imprimir o menu principal.
     */
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

    /**
     * Método para ler a opção do menu.
     */
    private static OpcaoMenu lerOpcaoMenu() {
        long opcaoDigitada = TecladoUtil.leituraLong("Informe a opção desejada: ");
        return OpcaoMenu.obterPorCodigo(opcaoDigitada);
    }

    /**
     * Método para cadastrar um aluno.
     */
    private static void cadastrarAluno() {
        System.out.println(OpcaoMenu.CADASTRAR_ALUNO.getDescricao());
        System.out.print("Nome: ");
        String nome = TecladoUtil.leitura().trim();
        System.out.print("CPF (apenas números): ");
        String cpf = TecladoUtil.leitura().trim();
        boolean confirmado = TecladoUtil.confirmar("Deseja realmente cadastrar este aluno");
        alunoService.cadastrar(nome, cpf, confirmado);
    }

    /**
     * Método para excluir um aluno.
     */
    private static void excluirAluno() {
        System.out.println(OpcaoMenu.EXCLUIR_ALUNO.getDescricao());
        System.out.print("CPF do aluno: ");
        String cpf = TecladoUtil.leitura().trim();
        alunoService.remover(cpf);
    }

    /**
     * Método para cadastrar um curso.
     */
    private static void cadastrarCurso() {
        System.out.println(OpcaoMenu.CADASTRAR_CURSO.getDescricao());
        System.out.print("Nome do curso: ");
        String nome = TecladoUtil.leitura().trim();
        if (!TecladoUtil.confirmar("Deseja realmente cadastrar este curso")) {
            System.out.println("Cadastro cancelado pelo usuário.");
            return;
        }

        cursoService.cadastrar(nome);
    }

    /** Método para excluir um curso */
    private static void excluirCurso() {
        System.out.println(OpcaoMenu.EXCLUIR_CURSO.getDescricao());
        System.out.print("Nome do curso: ");
        String nome = TecladoUtil.leitura().trim();
        if (!TecladoUtil.confirmar("Deseja realmente excluir este curso")) {
            System.out.println("Exclusão cancelada pelo usuário.");
            return;
        }
        cursoService.remover(nome);
    }

    /** Método para listar alunos */
    private static void listarAlunos() {
        alunoService.listar();
    }

    /** Método para listar cursos */
    private static void listarCursos() {
        cursoService.listarComAlunos();
    }

    /** Método para realizar matrícula de um aluno em um curso */
    private static void realizarMatricula() {
        matriculaService.matricularAluno();
    }

    /** Método para cancelar matrícula de um aluno em um curso */
    private static void cancelarMatricula() {
        matriculaService.cancelarMatricula();
    }
}