package utilsTest;

import dominio.Curso;
import utils.CursoUtils;
import excepciones.ExcepcionCursoDuplicado;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CursoUtilsTest {

    @Test
    void testImportarCursoValido() throws IOException, ExcepcionCursoDuplicado {
        Curso curso = CursoUtils.importarCurso("curso-test.yaml");
        assertEquals("Curso Test", curso.getNombre());
        assertEquals("Un curso de prueba", curso.getDescripcion());
        assertEquals(1, curso.getPreguntas().size());
    }

    @Test
    void testImportarCursoInexistente() {
        assertThrows(IOException.class, () -> {
            CursoUtils.importarCurso("no-existe.yaml");
        });
    }

    @Test
    void testCursoDuplicadoLanzaExcepcion() throws IOException, ExcepcionCursoDuplicado {
        Curso curso1 = CursoUtils.importarCurso("curso-duplicado.yaml");
        assertNotNull(curso1);

        assertThrows(ExcepcionCursoDuplicado.class, () -> {
            CursoUtils.importarCurso("curso-duplicado.yaml");
        });
    }
}
