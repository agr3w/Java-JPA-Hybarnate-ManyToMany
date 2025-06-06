package service;

import java.util.List;
import java.util.Scanner;

import entity.Aluno;
import entity.Curso;
import repository.AlunoRepository;
import repository.CursoRepository;

public class AlunoService {

    private AlunoRepository alunoRepository = new AlunoRepository();
    private CursoRepository cursoRepository = new CursoRepository();
    private Scanner scanner = new Scanner(System.in);

    public void cadastrar(Aluno aluno) throws Exception {
        try {
            System.out.println("\n--- Cadastro de Aluno ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.isBlank() || nome.length() > 100)
                throw new Exception("Nome inválido!");

            System.out.print("CPF (apenas números): ");
            String cpf = scanner.nextLine().trim();
            if (cpf.length() != 11 || !cpf.matches("\\d+"))
                throw new Exception("CPF inválido!");

            if (alunoRepository.buscarPorCpf(cpf) != null)
                throw new Exception("CPF já cadastrado!");

            System.out.println("Nome: " + nome);
            System.out.println("CPF: " + cpf);
            if (!confirmarOperacao("Os dados estão corretos"))
                throw new Exception("Cadastro cancelado");

            Aluno novoAluno = new Aluno();
            novoAluno.setNome(nome);
            novoAluno.setCpf(cpf);

            alunoRepository.inserir(novoAluno);
            System.out.println("Aluno cadastrado com sucesso! ID: " + novoAluno.getId());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Aluno> listar() {
        System.out.println("\n--- Lista de Alunos ---");
        List<Aluno> alunos = alunoRepository.listarTodos();
        if (alunos == null || alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return alunos;
        }
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
        return alunos;
    }

    public void remover() {
        try {
            System.out.println("\n--- Excluir Aluno ---");
            Aluno aluno = confirmarAlunoPorCPF();
            if (!aluno.getCursos().isEmpty())
                throw new Exception("Não é possível excluir aluno com matrículas ativas.");
            if (!confirmarOperacao("Tem certeza que deseja excluir o aluno " + aluno.getNome()))
                throw new Exception("Operação cancelada.");
            alunoRepository.remover(aluno);
            System.out.println("Aluno excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void matricular() {
        try {
            System.out.println("\n--- Matricular Aluno em Curso ---");
            Aluno aluno = confirmarAlunoPorCPF();
            List<Curso> cursos = cursoRepository.listarTodos();
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso disponível para matrícula.");
                return;
            }
            Curso curso = selecionarCursoPorMenu(cursos);
            boolean jaMatriculado = alunoRepository.verificarMatriculaExistente(aluno.getId(), curso.getId());
            if (jaMatriculado) {
                System.out.println("Aluno já está matriculado neste curso.");
                return;
            }
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
            Curso curso = selecionarCursoPorMenu(cursosMatriculados);
            if (!confirmarOperacao("Confirmar cancelamento da matrícula em " + curso.getNome()))
                throw new Exception("Operação cancelada.");
            aluno.removerCurso(curso);
            alunoRepository.atualizar(aluno);
            System.out.println("Matrícula cancelada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private boolean confirmarOperacao(String mensagem) {
        System.out.print(mensagem + " (S/N)? ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }

    private Curso selecionarCursoPorMenu(List<Curso> cursos) throws Exception {
        System.out.println("\nCursos disponíveis:");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println((i + 1) + " - " + cursos.get(i).getNome());
        }
        System.out.print("Selecione o número do curso: ");
        int escolha = Integer.parseInt(scanner.nextLine()) - 1;
        if (escolha < 0 || escolha >= cursos.size())
            throw new Exception("Opção inválida!");
        return cursos.get(escolha);
    }

    private Aluno confirmarAlunoPorCPF() throws Exception {
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine().trim();
        Aluno aluno = alunoRepository.buscarPorCpf(cpf);
        if (aluno == null)
            throw new Exception("Aluno não encontrado.");
        System.out.println("Aluno encontrado: " + aluno.getNome());
        System.out.print("Confirmar (S/N)? ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (!resposta.equals("S"))
            throw new Exception("Operação cancelada pelo usuário.");
        return aluno;
    }
}