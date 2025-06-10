package repository;

import java.util.List;

import entity.Curso;

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
}