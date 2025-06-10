package service;

import java.util.List;

import entity.Curso;
import repository.CursoRepository;

public class CursoService {

    private CursoRepository cursoRepository = new CursoRepository();

    public void cadastrar(String nome) {
        try {
            if (nome == null || nome.isBlank() || nome.length() > 100)
                throw new Exception("Nome do curso inválido! O nome deve ter até 100 caracteres. ");
            if (cursoRepository.buscarPorNome(nome) != null)
                throw new Exception("Já existe um curso com este nome. Por favor, escolha outro nome.");

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
                System.out.println("Nenhum curso cadastrado." +
                        " Por favor, cadastre um curso primeiro.");
                return;
            }
            for (Curso curso : cursos) {
                System.out.println("\nCurso: " + curso.getNome() + " (ID: " + curso.getId() + ")");
                System.out.println("Alunos matriculados (" + curso.getAlunos().size() + "):");
                if (curso.getAlunos().isEmpty()) {
                    System.out.println("  Nenhum aluno matriculado. Por favor, cadastre alunos primeiro.");
                } else {
                    curso.getAlunos().forEach(
                            aluno -> System.out.println("  - " + aluno.getNome() + " (CPF: " + aluno.getCpf() + ")"));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
    }

    public void remover(String nome) {
        try {
            Curso curso = cursoRepository.buscarPorNome(nome);
            if (curso == null)
                throw new Exception("Curso não encontrado! Verifique o nome informado.");
            if (!curso.getAlunos().isEmpty())
                throw new Exception(
                        "Não é possível excluir curso com alunos matriculados! Por favor, cancele as matrículas primeiro.");
            cursoRepository.remover(curso);
            System.out.println("Curso excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Curso> listarTodos() {
        return cursoRepository.listarTodos();
    }

    public Curso selecionarCursoPorMenu(List<Curso> cursos) throws Exception {
        System.out.println("\nCursos disponíveis:");
        for (int i = 0; i < cursos.size(); i++) {
            System.out.println((i + 1) + " - " + cursos.get(i).getNome());
        }
        System.out.print("Selecione o número do curso: ");
        int escolha = Integer.parseInt(util.TecladoUtil.leitura().trim()) - 1;
        if (escolha < 0 || escolha >= cursos.size()) {
            throw new Exception("Opção inválida!");
        }
        return cursos.get(escolha);
    }
}