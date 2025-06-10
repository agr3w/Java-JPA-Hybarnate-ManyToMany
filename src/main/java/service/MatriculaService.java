package service;

import java.util.List;
import entity.Aluno;
import entity.Curso;
import repository.AlunoRepository;
import repository.CursoRepository;

public class MatriculaService {

    private AlunoRepository alunoRepository;
    private CursoRepository cursoRepository;
    private AlunoService alunoService = new AlunoService();
    private CursoService cursoService = new CursoService();

    public MatriculaService() {
        this.alunoRepository = new AlunoRepository();
        this.cursoRepository = new CursoRepository();
    }

    public void matricularAluno() {
        try {
            System.out.println("\n--- Matricular Aluno em Curso ---");

            Aluno aluno = alunoService.confirmarAlunoPorCPF();
            if (aluno == null)
                return;

            List<Curso> cursos = cursoRepository.listarTodos();
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso disponível para matrícula.");
                return;
            }

            Curso curso = cursoService.selecionarCursoPorMenu(cursos);

            boolean jaMatriculado = alunoRepository.verificarMatriculaExistente(aluno.getId(), curso.getId());
            if (jaMatriculado) {
                System.out.println("Aluno já está matriculado neste curso.");
                return;
            }

            if (!util.TecladoUtil
                    .confirmar("Deseja realmente matricular este aluno no curso " + curso.getNome() + "?")) {
                System.out.println("Operação cancelada.");
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
            Aluno aluno = alunoService.confirmarAlunoPorCPF();
            if (aluno == null)
                return;

            List<Curso> cursosMatriculados = aluno.getCursos();
            if (cursosMatriculados.isEmpty()) {
                System.out.println("Aluno não possui matrículas ativas.");
                return;
            }

            System.out.println("Cursos matriculados:");
            Curso curso = cursoService.selecionarCursoPorMenu(cursosMatriculados);

            if (!util.TecladoUtil.confirmar("Confirmar cancelamento da matrícula em " + curso.getNome() + "?")) {
                System.out.println("Operação cancelada.");
                return;
            }

            aluno.removerCurso(curso);
            alunoRepository.atualizar(aluno);
            System.out.println("Matrícula cancelada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public List<Curso> listarCursos() {
        return cursoRepository.listarTodos();
    }
}