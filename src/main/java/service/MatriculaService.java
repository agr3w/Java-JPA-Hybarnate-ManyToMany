package service;

import java.util.List;

import org.hibernate.Session;

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
        try (Session session = util.HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n--- Matricular Aluno em Curso ---");

            // Buscar aluno pelo CPF
            System.out.print("CPF do aluno: ");
            String cpf = util.TecladoUtil.leitura().trim();
            Aluno aluno = session.createQuery("FROM Aluno WHERE cpf = :cpf", Aluno.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
            if (aluno == null) {
                System.out.println("Aluno não encontrado!");
                return;
            }
            System.out.println("Aluno encontrado: " + aluno.getNome());
            System.out.print("Confirmar (S/N)? ");
            String resposta = util.TecladoUtil.leitura().trim().toUpperCase();
            if (!resposta.equals("S")) {
                System.out.println("Operação cancelada pelo usuário.");
                return;
            }

            // Buscar cursos disponíveis
            List<Curso> cursos = session.createQuery("FROM Curso", Curso.class).getResultList();
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso disponível para matrícula.");
                return;
            }
            System.out.println("Cursos disponíveis:");
            for (int i = 0; i < cursos.size(); i++) {
                System.out.println((i + 1) + " - " + cursos.get(i).getNome());
            }
            System.out.print("Selecione o número do curso: ");
            int escolha = Integer.parseInt(util.TecladoUtil.leitura().trim()) - 1;
            if (escolha < 0 || escolha >= cursos.size()) {
                System.out.println("Opção inválida!");
                return;
            }
            Curso curso = cursos.get(escolha);

            // Confirmação
            if (!util.TecladoUtil
                    .confirmar("Deseja realmente matricular este aluno no curso " + curso.getNome() + "?")) {
                System.out.println("Operação cancelada.");
                return;
            }

            // Verifica se já está matriculado
            if (aluno.getCursos().contains(curso)) {
                System.out.println("Aluno já está matriculado neste curso.");
                return;
            }

            // Realiza matrícula
            session.beginTransaction();
            aluno.adicionarCurso(curso);
            session.merge(aluno);
            session.getTransaction().commit();
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