package controladorTest;

import static org.junit.jupiter.api.Assertions.*;

import controlador.Controlador;
import dominio.Curso;
import dominio.Dificultad;
import dominio.Pregunta;
import dominio.PreguntaTest;
import excepciones.CursoSinPreguntasCiertaDificultad;
import excepciones.ExcepcionCursoActualVacio;
import persistencia.AdaptadorCursoEnProgresoJPA;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

@TestInstance(Lifecycle.PER_CLASS)
class ControladorTest {

    private Controlador controlador;
    private Curso cursoDePrueba;
    private AdaptadorCursoEnProgresoJPA adaptadorCursoEnProgreso;
    
    @BeforeAll
    void setUp() {
        controlador = Controlador.getInstance();
        adaptadorCursoEnProgreso = AdaptadorCursoEnProgresoJPA.getIntance();
        
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaTest("¿2+2?", "4", List.of("3", "4", "5"), Dificultad.FACIL));
        preguntas.add(new PreguntaTest("¿3+5?", "8", List.of("6", "7", "8"), Dificultad.FACIL));
        preguntas.add(new PreguntaTest("¿10-2?", "8", List.of("7", "8", "9"), Dificultad.MEDIA));

        cursoDePrueba = new Curso("Curso Test", "Un curso de prueba", preguntas);
    }
    
    @Test
    void testIniciarCursoSinPreguntasFiltradas() {
        assertThrows(CursoSinPreguntasCiertaDificultad.class, () -> {
            controlador.iniciarCurso(cursoDePrueba, "Secuencial", "dificil"); // No hay preguntas "DIFICIL"
        });
    }
    
    @Test
    void testIniciarCursoCorrectamente() {
        assertDoesNotThrow(() -> {
            controlador.iniciarCurso(cursoDePrueba, "Secuencial","facil");
        });
    }

    @Test
    void testFiltrarPorDificultad() {
        List<Pregunta> faciles = controlador.filtrarPorDificultad(cursoDePrueba.getPreguntas(), Dificultad.FACIL);
        assertEquals(2, faciles.size());

        List<Pregunta> medias = controlador.filtrarPorDificultad(cursoDePrueba.getPreguntas(), Dificultad.MEDIA);
        assertEquals(1, medias.size());

        List<Pregunta> dificiles = controlador.filtrarPorDificultad(cursoDePrueba.getPreguntas(), Dificultad.DIFICIL);
        assertEquals(0, dificiles.size());
    }

    @Test
    void testCorregirRespuestaCorrecta() throws CursoSinPreguntasCiertaDificultad, ExcepcionCursoActualVacio {
        boolean resultado = controlador.corregir("4");
        assertTrue(resultado);
    }

    @Test
    void testCorregirRespuestaIncorrecta() throws CursoSinPreguntasCiertaDificultad, ExcepcionCursoActualVacio {

        boolean resultado = controlador.corregir("5");
        assertFalse(resultado);
    }

    @Test
    void testAvanzarProgreso() throws CursoSinPreguntasCiertaDificultad, ExcepcionCursoActualVacio {

        int progresoAntes = controlador.getProgreso();
        controlador.avanzarProgreso();

        int progresoDespues = controlador.getProgreso();
        assertEquals(progresoAntes + 1, progresoDespues);
    }

    @Test
    void testFinalizarSesionCurso() throws CursoSinPreguntasCiertaDificultad, ExcepcionCursoActualVacio {

        assertNull(controlador.finalizarSesionCurso());
        assertNotNull(controlador.getEstadistica());
       
    }
    
    @AfterAll
    void borrarBaseDeDatos() {
    	
    }
    
    
}
