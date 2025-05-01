package persistencia;

import dominio.CursoEnProgreso;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

public class AdaptadorCursoEnProgresoJPA implements ICursoEnProgreso {

    private static AdaptadorCursoEnProgresoJPA instancia;
    private final EntityManagerFactory emf;

    public static AdaptadorCursoEnProgresoJPA getIntance() {
        if (instancia == null) {
            instancia = new AdaptadorCursoEnProgresoJPA();
        }
        return instancia;
    }

    private AdaptadorCursoEnProgresoJPA() {
        this.emf = Persistence.createEntityManagerFactory("pds-unit");
    }

    @Override
    public void guardar(CursoEnProgreso entidad) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entidad);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al guardar CursoEnProgreso", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(CursoEnProgreso entidad) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entidad);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al actualizar CursoEnProgreso", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(CursoEnProgreso entidad) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CursoEnProgreso adjunto = em.contains(entidad) ? entidad : em.merge(entidad);
            em.remove(adjunto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al eliminar CursoEnProgreso", e);
        } finally {
            em.close();
        }
    }

    @Override
    public CursoEnProgreso buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CursoEnProgreso.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<CursoEnProgreso> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CursoEnProgreso c", CursoEnProgreso.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    @Override
    public CursoEnProgreso buscarPorCursoId(UUID cursoId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CursoEnProgreso> query = em.createQuery(
                "SELECT c FROM CursoEnProgreso c WHERE c.cursoId = :cursoId",
                CursoEnProgreso.class
            );
            query.setParameter("cursoId", cursoId);

            List<CursoEnProgreso> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);

        } finally {
            em.close();
        }
    }
    
    @Override
    public void eliminarPorCursoId(UUID cursoId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            TypedQuery<CursoEnProgreso> query = em.createQuery(
                "SELECT c FROM CursoEnProgreso c WHERE c.cursoId = :cursoId", CursoEnProgreso.class);
            query.setParameter("cursoId", cursoId);
            List<CursoEnProgreso> resultados = query.getResultList();

            for (CursoEnProgreso c : resultados) {
                CursoEnProgreso attached = em.contains(c) ? c : em.merge(c);
                em.remove(attached);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al eliminar por cursoId", e);
        } finally {
            em.close();
        }
    }

}
