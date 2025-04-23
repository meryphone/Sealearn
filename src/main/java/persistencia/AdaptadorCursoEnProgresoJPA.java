package persistencia;

import dominio.CursoEnProgreso;
import jakarta.persistence.*;

import java.util.List;

public class AdaptadorCursoEnProgresoJPA implements ICursoEnProgreso {

    private final EntityManager em;
    private static AdaptadorCursoEnProgresoJPA adaptadorCursoEnProgreso;

    public static AdaptadorCursoEnProgresoJPA getIntance() {
       if(adaptadorCursoEnProgreso == null) {
    	   adaptadorCursoEnProgreso = new AdaptadorCursoEnProgresoJPA();
       }
       return adaptadorCursoEnProgreso  ;
    }
    
    public AdaptadorCursoEnProgresoJPA(EntityManager em) {
    	this.em = em;
    }
    

    public AdaptadorCursoEnProgresoJPA() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pds-unit");
        this.em = emf.createEntityManager();
    }



	@Override
    public void guardar(CursoEnProgreso cursoEnProgreso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cursoEnProgreso);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al guardar CursoEnProgreso", e);
        }
    }

    @Override
    public void actualizar(CursoEnProgreso cursoEnProgreso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cursoEnProgreso);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al actualizar CursoEnProgreso", e);
        }
    }

    @Override
    public void eliminar(CursoEnProgreso cursoEnProgreso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CursoEnProgreso adjunto = em.contains(cursoEnProgreso) ? cursoEnProgreso : em.merge(cursoEnProgreso);
            em.remove(adjunto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al eliminar CursoEnProgreso", e);
        }
    }

    @Override
    public CursoEnProgreso buscarPorId(Long id) {
        return em.find(CursoEnProgreso.class, id);
    }

    @Override
    public List<CursoEnProgreso> buscarTodos() {
        return em.createQuery("SELECT c FROM CursoEnProgreso c", CursoEnProgreso.class).getResultList();
    }
}
