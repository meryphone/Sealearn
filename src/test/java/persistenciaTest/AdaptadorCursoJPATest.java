package persistenciaTest;

import dominio.Curso;
import org.junit.jupiter.api.*;
import jakarta.persistence.*;
import persistencia.AdaptadorCursoJPA;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdaptadorCursoJPATest {

    private AdaptadorCursoJPA adaptador;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    void setup() {
        emf = Persistence.createEntityManagerFactory("pds-unit");
        em = emf.createEntityManager();
        adaptador = AdaptadorCursoJPA.getInstance();
    }

    @BeforeEach
    void cleanBefore() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createQuery("DELETE FROM Curso").executeUpdate();
        tx.commit();
    }

    @AfterAll
    void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    void testGuardarYBuscarCurso() {
        Curso curso = new Curso("Curso de prueba");
        adaptador.guardar(curso);

        Curso encontrado = adaptador.buscarPorId(curso.getId());

        assertNotNull(encontrado);
        assertEquals("Curso de prueba", encontrado.getNombre());
    }

    @Test
    void testEliminarCurso() {
        Curso curso = new Curso("Curso para eliminar");
        adaptador.guardar(curso);
        adaptador.eliminar(curso);

        Curso eliminado = adaptador.buscarPorId(curso.getId());
        assertNull(eliminado);
    }

    @Test
    void testBuscarTodos() {
        Curso curso1 = new Curso("Curso 1");
        Curso curso2 = new Curso("Curso 2");
        adaptador.guardar(curso1);
        adaptador.guardar(curso2);

        List<Curso> cursos = adaptador.buscarTodos();
        assertEquals(2, cursos.size());
    }
}