package service;

import java.util.List;
import java.util.Scanner;

import entity.Curso;
import repository.CursoRepository;

public class CursoService {

    private CursoRepository cursoRepository = new CursoRepository();
    private Scanner scanner = new Scanner(System.in);

    public void cadastrar() {
        try {
            System.out.println("\n--- Cadastro de Curso ---");
            System.out.print("Nome do curso: ");
            String nome = scanner.nextLine().trim();
            if (cursoRepository.buscarPorNome(nome) != null) {
                System.out.println("Já existe um curso com este nome.");
                return;
            }
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

    public void listarComAlunos() {
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
                    curso.getAlunos().forEach(aluno -> 
                        System.out.println("  - " + aluno.getNome() + " (CPF: " + aluno.getCpf() + ")")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    public void remover() {
        try {
            System.out.println("\n--- Excluir Curso ---");
            System.out.print("Nome do curso: ");
            String nome = scanner.nextLine().trim();
            Curso curso = cursoRepository.buscarPorNome(nome);
            if (curso == null) throw new Exception("Curso não encontrado!");
            if (!curso.getAlunos().isEmpty()) throw new Exception("Não é possível excluir curso com alunos matriculados!");
            if (!confirmarOperacao("Tem certeza que deseja excluir o curso " + curso.getNome())) throw new Exception("Operação cancelada.");
            cursoRepository.remover(curso);
            System.out.println("Curso excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private boolean confirmarOperacao(String mensagem) {
        System.out.print(mensagem + " (S/N)? ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }
}