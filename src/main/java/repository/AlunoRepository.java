package repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import entity.Aluno;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class AlunoRepository {
    
    private Session session;
    
    public AlunoRepository() {
        this.session = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory()
                .openSession();
    }
    
    public void inserir(Aluno aluno) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(aluno);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao inserir aluno: " + e.getMessage());
        }
    }
    
    public void atualizar(Aluno aluno) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(aluno);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }
    
    public void remover(Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Aluno aluno = session.find(Aluno.class, id);
            if (aluno != null) {
                session.remove(aluno);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao remover aluno: " + e.getMessage());
        }
    }
    
    public Aluno buscarPorId(Long id) {
        try {
            return session.find(Aluno.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar aluno por ID: " + e.getMessage());
            return null;
        }
    }
    
    public Aluno buscarPorCpf(String cpf) {
        try {
            return session.createQuery("FROM Aluno WHERE cpf = :cpf", Aluno.class)
                    .setParameter("cpf", cpf)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Erro ao buscar aluno por CPF: " + e.getMessage());
            return null;
        }
    }
    
    public List<Aluno> listarTodos() {
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Aluno> criteria = builder.createQuery(Aluno.class);
            criteria.from(Aluno.class);
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
            return null;
        }
    }
    
    public void close() {
        session.close();
    }
}