package persistenciaTest;

import dominio.Estadistica;
import org.junit.jupiter.api.*;
import jakarta.persistence.*;
import persistencia.AdaptadorEstadisticaJPA;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdaptadorEstadisticaJPATest {

    private AdaptadorEstadisticaJPA adaptador;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    void setup() {
        emf = Persistence.createEntityManagerFactory("pds-unit");
        em = emf.createEntityManager();
        adaptador = AdaptadorEstadisticaJPA.getIntance();
    }

    @BeforeEach
    void cleanBefore() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createQuery("DELETE FROM Estadistica").executeUpdate();
        tx.commit();
    }

    @AfterAll
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void testGuardarYBuscarPorId() {
        Estadistica estadistica = new Estadistica();
        adaptador.guardar(estadistica);

        Estadistica encontrada = adaptador.buscarPorId(estadistica.getId());

        assertNotNull(encontrada);
    }

    @Test
    void testActualizar() {
        Estadistica estadistica = new Estadistica();
        adaptador.guardar(estadistica);

        estadistica.setRachaActual(10);
        adaptador.actualizar(estadistica);

        Estadistica actualizada = adaptador.buscarPorId(estadistica.getId());
        assertEquals(10, actualizada.getRachaActual());
    }

    @Test
    void testEliminar() {
        Estadistica estadistica = new Estadistica();
        adaptador.guardar(estadistica);

        adaptador.eliminar(estadistica);
        assertNull(adaptador.buscarPorId(estadistica.getId()));
    }

    @Test
    void testBuscarTodos() {
        adaptador.guardar(new Estadistica());
        adaptador.guardar(new Estadistica());

        List<Estadistica> lista = adaptador.buscarTodos();
        assertEquals(2, lista.size());
    }
}
