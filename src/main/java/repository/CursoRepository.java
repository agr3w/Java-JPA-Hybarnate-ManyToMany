package repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import entity.Curso;
import util.HibernateUtil;

public class CursoRepository extends GenericRepository<Curso> {

    public CursoRepository() {
        super(Curso.class);
    }

    public Curso buscarPorId(Long id) {
        return pesquisaPeloId(id);
    }

    public Curso buscarPorNome(String nome) {
        try {
            return session.createQuery("FROM Curso WHERE nome = :nome", Curso.class)
                    .setParameter("nome", nome)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Erro ao buscar curso por nome: " + e.getMessage());
            return null;
        }
    }

    public List<Curso> listarTodos() {
        return pesquisarTodos();
    }

    public List<Curso> listarTodosComAlunos() {
        List<Curso> cursos = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            cursos = session.createQuery(
                    "SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.alunos", Curso.class).getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao consultar cursos com alunos: " + e.getMessage());
        }
        return cursos;
    }
}