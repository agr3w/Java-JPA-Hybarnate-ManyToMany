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
    
    public void cadastrarAluno() {
        System.out.println("\n--- Cadastro de Aluno ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF (apenas números): ");
        String cpf = scanner.nextLine();
        
        // Verifica se o CPF já existe
        if (alunoRepository.buscarPorCpf(cpf) != null) {
            System.out.println("Erro: Já existe um aluno cadastrado com este CPF.");
            return;
        }
        
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setCpf(cpf);
        
        alunoRepository.inserir(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }
    
    public void cadastrarCurso() {
        System.out.println("\n--- Cadastro de Curso ---");
        System.out.print("Nome do curso: ");
        String nome = scanner.nextLine();
        
        // Verifica se o curso já existe
        if (cursoRepository.buscarPorNome(nome) != null) {
            System.out.println("Erro: Já existe um curso com este nome.");
            return;
        }
        
        Curso curso = new Curso();
        curso.setNome(nome);
        
        cursoRepository.inserir(curso);
        System.out.println("Curso cadastrado com sucesso!");
    }
    
    public void matricularAluno() {
        System.out.println("\n--- Matricular Aluno em Curso ---");
        
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine();
        Aluno aluno = alunoRepository.buscarPorCpf(cpf);
        
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        
        System.out.print("Nome do curso: ");
        String nomeCurso = scanner.nextLine();
        Curso curso = cursoRepository.buscarPorNome(nomeCurso);
        
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }
        
        // Verifica se o aluno já está matriculado no curso
        if (aluno.getCursos().contains(curso)) {
            System.out.println("Aluno já está matriculado neste curso.");
            return;
        }
        
        aluno.adicionarCurso(curso);
        alunoRepository.atualizar(aluno);
        System.out.println("Matrícula realizada com sucesso!");
    }
    
    public void cancelarMatricula() {
        System.out.println("\n--- Cancelar Matrícula ---");
        
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine();
        Aluno aluno = alunoRepository.buscarPorCpf(cpf);
        
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        
        System.out.print("Nome do curso: ");
        String nomeCurso = scanner.nextLine();
        Curso curso = cursoRepository.buscarPorNome(nomeCurso);
        
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }
        
        // Verifica se o aluno está matriculado no curso
        if (!aluno.getCursos().contains(curso)) {
            System.out.println("Aluno não está matriculado neste curso.");
            return;
        }
        
        aluno.removerCurso(curso);
        alunoRepository.atualizar(aluno);
        System.out.println("Matrícula cancelada com sucesso!");
    }
    
    public void excluirAluno() {
        System.out.println("\n--- Excluir Aluno ---");
        
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine();
        Aluno aluno = alunoRepository.buscarPorCpf(cpf);
        
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        
        // Remove o aluno de todos os cursos antes de excluir
        for (Curso curso : aluno.getCursos()) {
            aluno.removerCurso(curso);
        }
        
        alunoRepository.remover(aluno.getId());
        System.out.println("Aluno excluído com sucesso!");
    }
    
    public void excluirCurso() {
        System.out.println("\n--- Excluir Curso ---");
        
        System.out.print("Nome do curso: ");
        String nome = scanner.nextLine();
        Curso curso = cursoRepository.buscarPorNome(nome);
        
        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }
        
        // Remove todos os alunos do curso antes de excluir
        for (Aluno aluno : curso.getAlunos()) {
            aluno.removerCurso(curso);
            alunoRepository.atualizar(aluno);
        }
        
        cursoRepository.remover(curso.getId());
        System.out.println("Curso excluído com sucesso!");
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
        System.out.println("\n--- Lista de Cursos e Alunos ---");
        List<Curso> cursos = cursoRepository.listarTodos();
        
        if (cursos == null || cursos.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }
        
        for (Curso curso : cursos) {
            System.out.println("\nCurso: " + curso.getNome());
            System.out.println("Alunos matriculados:");
            
            if (curso.getAlunos().isEmpty()) {
                System.out.println("  Nenhum aluno matriculado.");
            } else {
                for (Aluno aluno : curso.getAlunos()) {
                    System.out.println("  - " + aluno.getNome() + " (CPF: " + aluno.getCpf() + ")");
                }
            }
        }
    }
    
    public void close() {
        alunoRepository.close();
        cursoRepository.close();
        scanner.close();
    }
}