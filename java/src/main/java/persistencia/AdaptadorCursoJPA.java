package persistencia;

import dominio.Curso;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

public class AdaptadorCursoJPA implements ICurso {

    private static AdaptadorCursoJPA instancia;
    private final EntityManagerFactory emf;

    private AdaptadorCursoJPA() {
        this.emf = Persistence.createEntityManagerFactory("pds-unit");
    }

    public static AdaptadorCursoJPA getInstance() {
        if (instancia == null) {
            instancia = new AdaptadorCursoJPA();
        }
        return instancia;
    }

    @Override
    public void guardar(Curso curso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(curso);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al guardar Curso", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Curso curso) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Curso cursoAdjunto = em.find(Curso.class, curso.getId());
            if (cursoAdjunto != null) {
                em.remove(cursoAdjunto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al eliminar Curso", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Curso buscarPorId(UUID id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT c FROM Curso c LEFT JOIN FETCH c.preguntas WHERE c.id = :id", Curso.class)
                .setParameter("id", id)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Curso> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.preguntas", Curso.class)
                .getResultList();
        } finally {
            em.close();
        }
    }
}
