package persistencia;

import dominio.Estadistica;
import jakarta.persistence.*;
import java.util.List;

public class AdaptadorEstadisticaJPA implements IEstadistica {

    private static AdaptadorEstadisticaJPA instancia;
    private final EntityManagerFactory emf;

    public static AdaptadorEstadisticaJPA getIntance() {
        if (instancia == null) {
            instancia = new AdaptadorEstadisticaJPA();
        }
        return instancia;
    }

    private AdaptadorEstadisticaJPA() {
        this.emf = Persistence.createEntityManagerFactory("pds-unit");
    }

    @Override
    public void guardar(Estadistica entidad) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entidad);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al guardar Estadística", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(Estadistica entidad) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entidad);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al actualizar Estadística", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Estadistica entidad) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Estadistica adjunta = em.contains(entidad) ? entidad : em.merge(entidad);
            em.remove(adjunta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al eliminar Estadística", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Estadistica buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Estadistica.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Estadistica> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Estadistica e", Estadistica.class).getResultList();
        } finally {
            em.close();
        }
    }
}
