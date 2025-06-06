package service;

import java.util.List;
import java.util.Scanner;

import entity.Aluno;
import entity.Curso;
import repository.AlunoRepository;
import repository.CursoRepository;

public class MatriculaService {

    private AlunoRepository alunoRepository;
    private CursoRepository cursoRepository;
    private Scanner scanner;

    public MatriculaService() {
        this.alunoRepository = new AlunoRepository();
        this.cursoRepository = new CursoRepository();
        this.scanner = new Scanner(System.in);
    }

    private boolean confirmarOperacao(String mensagem) {
        System.out.print(mensagem + " (S/N)? ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }

    public void cadastrarAluno() {
        try {
            System.out.println("\n--- Cadastro de Aluno ---");

            // Validação do nome
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();

            if (nome.isBlank()) {
                throw new Exception("Nome não pode ser vazio!");
            }
            if (nome.length() > 100) {
                throw new Exception("Nome deve ter até 100 caracteres!");
            }

            // Validação do CPF
            System.out.print("CPF (apenas números): ");
            String cpf = scanner.nextLine().trim();

            if (cpf.length() != 11 || !cpf.matches("\\d+")) {
                throw new Exception("CPF inválido! Deve conter 11 dígitos numéricos");
            }

            // Verifica se CPF já existe
            if (alunoRepository.buscarPorCpf(cpf) != null) {
                throw new Exception("CPF já cadastrado!");
            }

            // Confirmação dos dados
            System.out.println("\nConfirme os dados:");
            System.out.println("Nome: " + nome);
            System.out.println("CPF: " + cpf);
            if (!confirmarOperacao("Os dados estão corretos")) {
                throw new Exception("Cadastro cancelado pelo usuário");
            }

            // Cria e persiste o aluno
            Aluno novoAluno = new Aluno();
            novoAluno.setNome(nome);
            novoAluno.setCpf(cpf);

            alunoRepository.inserir(novoAluno);
            System.out.println("Aluno cadastrado com sucesso! ID: " + novoAluno.getId());

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarCurso() {
        try {
            System.out.println("\n--- Cadastro de Curso ---");
            System.out.print("Nome do curso: ");
            String nome = scanner.nextLine().trim();

            // verifica se o curso ja existe
            if (cursoRepository.buscarPorNome(nome) != null) {
                System.out.println("Já existe um curso com este nome.");
                return;
            }

            // Confirmação
            if (!confirmarOperacao("Confirmar criação do curso: " + nome)) {
                System.out.println("Cadastro cancelado.");
                return;
            }

            Curso curso = new Curso();
            curso.setNome(nome);

            cursoRepository.inserir(curso);
            System.out.println("Curso cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void matricularAluno() {
        try {
            System.out.println("\n--- Matricular Aluno em Curso ---");

            // Confirmação do aluno
            Aluno aluno = confirmarAlunoPorCPF();

            // Listar cursos disponíveis
            List<Curso> cursos = cursoRepository.listarTodos();
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso disponível para matrícula.");
                return;
            }

            // Seleção do curso
            Curso curso = selecionarCursoPorMenu(cursos);

            // Verificação no banco de dados (não apenas em memória)
            boolean jaMatriculado = alunoRepository.verificarMatriculaExistente(aluno.getId(), curso.getId());
            if (jaMatriculado) {
                System.out.println("Aluno já está matriculado neste curso.");
                return;
            }

            if (jaMatriculado) {
                System.out.println("Aluno ID " + aluno.getId() +
                        " já matriculado no curso ID " + curso.getId());
                return;
            }

            // Efetivar matrícula
            aluno.adicionarCurso(curso);
            alunoRepository.atualizar(aluno);
            System.out.println("Matrícula realizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void cancelarMatricula() {
        try {
            System.out.println("\n--- Cancelar Matrícula ---");
            Aluno aluno = confirmarAlunoPorCPF();

            List<Curso> cursosMatriculados = aluno.getCursos();
            if (cursosMatriculados.isEmpty()) {
                System.out.println("Aluno não possui matrículas ativas.");
                return;
            }

            System.out.println("Cursos matriculados:");
            Curso curso = selecionarCursoPorMenu(cursosMatriculados);

            // Confirmação adicional
            if (!confirmarOperacao("Confirmar cancelamento da matrícula em " + curso.getNome())) {
                throw new Exception("Operação cancelada.");
            }

            aluno.removerCurso(curso);
            alunoRepository.atualizar(aluno);
            System.out.println("Matrícula cancelada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void excluirAluno() {
        try {
            System.out.println("\n--- Excluir Aluno ---");
            Aluno aluno = confirmarAlunoPorCPF();

            // Verificar matrículas ativas
            if (!aluno.getCursos().isEmpty()) {
                throw new Exception("Não é possível excluir aluno com matrículas ativas.");
            }

            // Confirmação adicional
            if (!confirmarOperacao("Tem certeza que deseja excluir o aluno " + aluno.getNome())) {
                throw new Exception("Operação cancelada.");
            }

            alunoRepository.remover(aluno);
            System.out.println("Aluno excluído com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void excluirCurso() {
        try {
            System.out.println("\n--- Excluir Curso ---");
            System.out.print("Nome do curso: ");
            String nome = scanner.nextLine().trim();
            Curso curso = cursoRepository.buscarPorNome(nome);

            if (curso == null) {
                throw new Exception("Curso não encontrado!");
            }

            if (!curso.getAlunos().isEmpty()) {
                throw new Exception("Não é possível excluir curso com alunos matriculados!");
            }

            // Confirmação adicional
            if (!confirmarOperacao("Tem certeza que deseja excluir o curso " + curso.getNome())) {
                throw new Exception("Operação cancelada.");
            }

            cursoRepository.remover(curso);
            System.out.println("Curso excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarAlunos() {
        System.out.println("\n--- Lista de Alunos ---");
        List<Aluno> alunos = alunoRepository.listarTodos();

        if (alunos == null || alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
    }

    public void listarCursosComAlunos() {
        try {
            System.out.println("\n--- Lista de Cursos e Alunos ---");
            List<Curso> cursos = cursoRepository.listarTodos();

            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso cadastrado.");
                return;
            }

            for (Curso curso : cursos) {
                System.out.println("\nCurso: " + curso.getNome() + " (ID: " + curso.getId() + ")");
                System.out.println("Alunos matriculados (" + curso.getAlunos().size() + "):");

                if (curso.getAlunos().isEmpty()) {
                    System.out.println("  Nenhum aluno matriculado.");
                } else {
                    for (Aluno aluno : curso.getAlunos()) {
                        System.out.println("  - " + aluno.getNome() + " (CPF: " + aluno.getCpf() + ")");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    public void close() {
        try {
            scanner.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }

    private Curso selecionarCursoPorMenu(List<Curso> cursos) throws Exception {
        System.out.println("\nCursos disponíveis:");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println((i + 1) + " - " + cursos.get(i).getNome());
        }

        System.out.print("Selecione o número do curso: ");
        int escolha = Integer.parseInt(scanner.nextLine()) - 1;

        if (escolha < 0 || escolha >= cursos.size()) {
            throw new Exception("Opção inválida!");
        }

        return cursos.get(escolha);
    }

    private Aluno confirmarAlunoPorCPF() throws Exception {
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine().trim();

        Aluno aluno = alunoRepository.buscarPorCpf(cpf);
        if (aluno == null) {
            throw new Exception("Aluno não encontrado.");
        }

        System.out.println("Aluno encontrado: " + aluno.getNome());
        System.out.print("Confirmar (S/N)? ");
        String resposta = scanner.nextLine().trim().toUpperCase();

        if (!resposta.equals("S")) {
            throw new Exception("Operação cancelada pelo usuário.");
        }

        return aluno;
    }
}