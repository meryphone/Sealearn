package dominioTest;

import dominio.Curso;
import dominio.PreguntaRespuestaCorta;
import dominio.Dificultad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CursoTest {

    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = new Curso(
            "Curso de ejemplo",
            "Descripción del curso",
            List.of(
                new PreguntaRespuestaCorta("¿Lenguaje de esta clase?", "Java", Dificultad.FACIL)
            )
        );
    }


    @Test
    void testUuidGenerado() {
        UUID id = curso.getId();
        assertNotNull(id);
    }

}
