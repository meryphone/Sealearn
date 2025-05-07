package dominioTest;

import dominio.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CursoEnProgresoTest {

    private CursoEnProgreso progreso;
    private List<Pregunta> preguntas;

    @BeforeEach
    void setUp() {
        preguntas = List.of(
            new PreguntaRespuestaCorta("¿Capital de Francia?", "París", Dificultad.FACIL),
            new PreguntaRespuestaCorta("¿2+2?", "4", Dificultad.MEDIA)
        );
        Estrategia estrategia = new Secuencial(preguntas.size());
        progreso = new CursoEnProgreso(UUID.randomUUID(), estrategia, preguntas, Dificultad.MEDIA);
    }

    @Test
    void testGetPreguntaActualInicial() {
        Pregunta actual = progreso.getPreguntaActual();
        assertNotNull(actual);
        assertEquals("¿Capital de Francia?", actual.getEnunciado());
    }

    @Test
    void testAvanzarPregunta() {
        progreso.avanzar();
        Pregunta siguiente = progreso.getPreguntaActual();
        assertEquals("¿2+2?", siguiente.getEnunciado());
    }

    @Test
    void testCursoCompletado() {
        progreso.avanzar();
        progreso.avanzar(); 
        assertTrue(progreso.isCompletado());
    }

}
