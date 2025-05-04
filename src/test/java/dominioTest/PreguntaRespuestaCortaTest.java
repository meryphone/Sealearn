package dominioTest;

import dominio.PreguntaRespuestaCorta;
import dominio.Dificultad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class PreguntaRespuestaCortaTest {
	//testeamos solo esta subclase de pregunta ya que todas comparten la misma funci�n sin modificarla
	private PreguntaRespuestaCorta pregunta;
	
	@BeforeEach
    void setUp() {
		pregunta = new PreguntaRespuestaCorta("�Lenguaje de esta clase?", "Java", Dificultad.FACIL);
	}

    @Test
    void testValidarRespuestaCorrecta() {
        assertTrue(pregunta.validarRespuesta("java")); //testeamos tambi�n las may�sculas y min�sculas
    }

    @Test
    void testValidarRespuestaIncorrecta() {
        assertFalse(pregunta.validarRespuesta("Python"));
    }
}
