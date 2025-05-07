package dominioTest;

import dominio.PreguntaRespuestaCorta;
import dominio.Dificultad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreguntaRespuestaCortaTest {

    private PreguntaRespuestaCorta pregunta;

    @BeforeEach
    void setUp() {
        pregunta = new PreguntaRespuestaCorta("¿Capital de España?", "Madrid", Dificultad.FACIL);
    }

    @Test
    void testRespuestaExacta() {
        assertTrue(pregunta.validarRespuesta("Madrid"));
    }

    @Test
    void testRespuestaConMayusculas() {
        assertTrue(pregunta.validarRespuesta("mAdRiD"));
    }

    @Test
    void testRespuestaIncorrecta() {
        assertFalse(pregunta.validarRespuesta("Barcelona"));
    }

    @Test
    void testRespuestaConEspacios() {
        assertFalse(pregunta.validarRespuesta(" Madrid ")); 
    }

    @Test
    void testRespuestaVacia() {
        assertFalse(pregunta.validarRespuesta(""));
    }

    @Test
    void testRespuestaNula() {
        assertThrows(NullPointerException.class, () -> {
            pregunta.validarRespuesta(null);
        });
    }
}
