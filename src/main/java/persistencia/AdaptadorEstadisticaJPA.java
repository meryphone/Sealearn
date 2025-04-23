package persistencia;

import dominio.Estadistica;
import jakarta.persistence.*;
import java.util.List;

public class AdaptadorEstadisticaJPA implements IEstadistica {

    private final EntityManager em;
    private static AdaptadorEstadisticaJPA adaptadorEstadistica;
    
    public static AdaptadorEstadisticaJPA getIntance() {
        if(adaptadorEstadistica == null) {
        	adaptadorEstadistica = new AdaptadorEstadisticaJPA();
        }
        return adaptadorEstadistica  ;
     }

    public AdaptadorEstadisticaJPA(EntityManager em) {
        this.em = em;
    }
    
    public AdaptadorEstadisticaJPA() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pds-unit");
        this.em = emf.createEntityManager();
    }

    @Override
    public void guardar(Estadistica estadistica) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(estadistica);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al guardar Estadística", e);
        }
    }

    @Override
    public void actualizar(Estadistica estadistica) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(estadistica);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al actualizar Estadística", e);
        }
    }

    @Override
    public void eliminar(Estadistica estadistica) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Estadistica adjunta = em.contains(estadistica) ? estadistica : em.merge(estadistica);
            em.remove(adjunta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al eliminar Estadística", e);
        }
    }

    @Override
    public Estadistica buscarPorId(Long id) {
        return em.find(Estadistica.class, id);
    }

    @Override
    public List<Estadistica> buscarTodos() {
        return em.createQuery("SELECT e FROM Estadistica e", Estadistica.class).getResultList();
    }
}
