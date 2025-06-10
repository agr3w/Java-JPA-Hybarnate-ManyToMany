package service;

import java.util.List;
import java.util.Scanner;

import entity.Aluno;
import repository.AlunoRepository;

public class AlunoService {

    private AlunoRepository alunoRepository = new AlunoRepository();

    private Scanner scanner = new Scanner(System.in);

    public void cadastrar(String nome, String cpf, boolean confirmado) {
        if (!confirmado) {
            System.out.println("Cadastro cancelado pelo usuário.");
            return;
        }
        try {
            if (nome.isBlank() || nome.length() > 100)
                throw new Exception("Nome inválido! O nome deve ter até 100 caracteres.");
            if (cpf.length() != 11 || !cpf.matches("\\d+"))
                throw new Exception("CPF inválido! O CPF deve ter 11 dígitos numéricos.");
            if (alunoRepository.buscarPorCpf(cpf) != null)
                throw new Exception("CPF já cadastrado! Por favor, informe outro CPF.");

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
            System.out.println("Nenhum aluno cadastrado. Por favor, cadastre um aluno primeiro.");
            return alunos;
        }
        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
        return alunos;
    }

    public void remover(String cpf) {
        try {
            Aluno aluno = alunoRepository.buscarPorCpf(cpf);
            if (aluno == null) {
                System.out.println("Aluno não encontrado!");
                return;
            }
            if (aluno.getCursos() != null && !aluno.getCursos().isEmpty()) {
                System.out.println("Não é possível excluir o aluno pois ele está matriculado em um ou mais cursos.");
                return;
            }
            alunoRepository.remover(aluno);
            System.out.println("Aluno excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
        }
    }

    public Aluno confirmarAlunoPorCPF() throws Exception {
        System.out.print("CPF do aluno: ");
        String cpf = scanner.nextLine().trim();
        Aluno aluno = alunoRepository.buscarPorCpf(cpf);
        if (aluno == null)
            throw new Exception("Aluno não encontrado! Verifique o CPF informado.");
        System.out.println("Aluno encontrado: " + aluno.getNome());
        System.out.print("Confirmar (S/N)? ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (!resposta.equals("S"))
            throw new Exception("Operação cancelada pelo usuário.");
        return aluno;
    }
}