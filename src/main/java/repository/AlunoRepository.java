package repository;

import java.util.List;

import org.hibernate.query.Query;

import entity.Aluno;

public class AlunoRepository extends GenericRepository<Aluno> {

    public AlunoRepository() {
        super(Aluno.class);
    }

    public Aluno buscarPorId(Long id) {
        return pesquisaPeloId(id);
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
        return pesquisarTodos();
    }

    public boolean verificarMatriculaExistente(Long idAluno, Long idCurso) {
        try {
            String sql = "SELECT COUNT(*) > 0 FROM TB_Matricula WHERE id_aluno = :idAluno AND id_curso = :idCurso";
            Query<Boolean> query = session.createNativeQuery(sql, Boolean.class);
            query.setParameter("idAluno", idAluno);
            query.setParameter("idCurso", idCurso);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Erro ao verificar matrícula: " + e.getMessage());
            return true; // Assume que já existe em caso de erro
        }
    }
}