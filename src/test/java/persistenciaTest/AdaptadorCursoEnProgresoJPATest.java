package persistenciaTest;

import dominio.CursoEnProgreso;
import org.junit.jupiter.api.*;
import jakarta.persistence.*;
import persistencia.AdaptadorCursoEnProgresoJPA;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdaptadorCursoEnProgresoJPATest {

    private AdaptadorCursoEnProgresoJPA adaptador;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    void setup() {
        emf = Persistence.createEntityManagerFactory("pds-unit");
        em = emf.createEntityManager();
        adaptador = AdaptadorCursoEnProgresoJPA.getIntance();
    }

    @BeforeEach
    void cleanBefore() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createQuery("DELETE FROM CursoEnProgreso").executeUpdate();
        tx.commit();
    }

    @AfterAll
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void testGuardarYBuscarPorId() {
    	String ejemplo = "ejemplo";
    	UUID id = UUID.nameUUIDFromBytes(ejemplo.getBytes());
        CursoEnProgreso curso = new CursoEnProgreso( id, 3);
        adaptador.guardar(curso);

        CursoEnProgreso encontrado = adaptador.buscarPorCursoId(id);

        assertNotNull(encontrado);
        assertEquals(curso.getCursoId(), encontrado.getCursoId());
        assertEquals(3, encontrado.getProgreso());
    }

    @Test
    void testActualizar() {
    	String ejemplo = "ejemplo";
    	UUID id = UUID.nameUUIDFromBytes(ejemplo.getBytes());
        CursoEnProgreso curso = new CursoEnProgreso(id, 2);
        adaptador.guardar(curso);

        curso.setProgreso(5);
        adaptador.actualizar(curso);

        CursoEnProgreso actualizado = adaptador.buscarPorCursoId(curso.getCursoId());
        assertEquals(5, actualizado.getProgreso());
    }

    @Test
    void testEliminar() {
        CursoEnProgreso curso = new CursoEnProgreso(UUID.randomUUID(), 0);
        adaptador.guardar(curso);

        adaptador.eliminar(curso);
        assertNull(adaptador.buscarPorCursoId(curso.getCursoId()));
    }

    @Test
    void testBuscarPorCursoId() {
        UUID cursoId = UUID.randomUUID();
        CursoEnProgreso curso = new CursoEnProgreso(cursoId, 1);
        adaptador.guardar(curso);

        CursoEnProgreso resultado = adaptador.buscarPorCursoId(cursoId);
        assertNotNull(resultado);
        assertEquals(cursoId, resultado.getCursoId());
    }

    @Test
    void testEliminarPorCursoId() {
        UUID cursoId = UUID.randomUUID();
        adaptador.guardar(new CursoEnProgreso(cursoId, 2));

        adaptador.eliminarPorCursoId(cursoId);
        assertNull(adaptador.buscarPorCursoId(cursoId));
    }

    @Test
    void testBuscarTodos() {
        adaptador.guardar(new CursoEnProgreso(UUID.randomUUID(), 0));
        adaptador.guardar(new CursoEnProgreso(UUID.randomUUID(), 1));

        List<CursoEnProgreso> lista = adaptador.buscarTodos();
        assertEquals(2, lista.size());
    }
}
