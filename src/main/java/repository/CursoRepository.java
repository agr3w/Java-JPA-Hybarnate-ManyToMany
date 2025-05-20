package repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import entity.Curso;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class CursoRepository {
    
    private Session session;
    
    public CursoRepository() {
        this.session = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory()
                .openSession();
    }
    
    public void inserir(Curso curso) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(curso);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao inserir curso: " + e.getMessage());
        }
    }
    
    public void atualizar(Curso curso) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(curso);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
        }
    }
    
    public void remover(Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Curso curso = session.find(Curso.class, id);
            if (curso != null) {
                session.remove(curso);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao remover curso: " + e.getMessage());
        }
    }
    
    public Curso buscarPorId(Long id) {
        try {
            return session.find(Curso.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar curso por ID: " + e.getMessage());
            return null;
        }
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
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Curso> criteria = builder.createQuery(Curso.class);
            criteria.from(Curso.class);
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
            return null;
        }
    }
    
    public void close() {
        session.close();
    }
}