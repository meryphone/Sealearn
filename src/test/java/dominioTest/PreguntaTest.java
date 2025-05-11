package dominioTest;

import dominio.PreguntaRespuestaCorta;
import dominio.Dificultad;
import dominio.Pregunta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class PreguntaTest {
	
    private Pregunta pregunta;

	@BeforeEach
    void setUp() {
		// Se realizan las pruebas con RespuestaCorta porque es el tipo de pregunta mas sencillo de instanciar
		// y el metodo de validar pregunta funciona igual para todas las subclases de pregunta
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
        assertFalse(pregunta.validarRespuesta(null));
        
    }
}
