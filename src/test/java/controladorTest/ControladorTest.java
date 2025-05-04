package controladorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controlador.Controlador;
import dominio.Curso;
import dominio.CursoEnProgreso;
import dominio.Dificultad;
import dominio.Estadistica;
import dominio.Pregunta;
import dominio.PreguntaTest;
import persistencia.ICursoEnProgreso;
import persistencia.IEstadistica;

class ControladorTest {

    private Controlador controlador;
    private Estadistica estadisticaMock;
    private ICursoEnProgreso cursoRepoMock;
    private IEstadistica estadisticaRepoMock;
    private Curso curso;
 
    @BeforeEach
    void setUp() {
        estadisticaMock = mock(Estadistica.class);
        cursoRepoMock = mock(ICursoEnProgreso.class);
        estadisticaRepoMock = mock(IEstadistica.class);

        List<Pregunta> preguntas = List.of(
            new PreguntaTest("¿2+2?", "4", List.of("3", "4", "5"), Dificultad.FACIL),
            new PreguntaTest("¿5+5?", "10", List.of("10", "11", "12"), Dificultad.FACIL)
        );
        curso = new Curso("Curso Test", "Descripción", new ArrayList<>(preguntas));

        controlador = new Controlador(estadisticaMock,cursoRepoMock,estadisticaRepoMock); 
    }

    @Test
    void iniciarCurso_deberiaGuardarYRegistrarEstudio() throws Exception {
        CursoEnProgreso cursoProgreso = controlador.iniciarCurso(curso, "Secuencial", "facil");
        
        assertNotNull(cursoProgreso);
        verify(estadisticaMock).registrarEstudioHoy();
        verify(cursoRepoMock).guardar(any());
    }

    @Test
    void corregir_deberiaActualizarEstadistica() throws Exception {
        controlador.iniciarCurso(curso, "Secuencial", "facil");

        boolean resultado = controlador.corregir("4");
        assertTrue(resultado);
        verify(estadisticaMock).registrarRespuesta(true);
    }

    @Test
    void avanzarProgreso_deberiaActualizarCursoYEstadistica() throws Exception {
        controlador.iniciarCurso(curso, "Secuencial", "facil");
        controlador.avanzarProgreso();

        verify(estadisticaRepoMock).actualizar(estadisticaMock);
        verify(cursoRepoMock).actualizar(any());
    }

    @Test
    void restablecerEstadisticas_deberiaResetearYActualizar() {
        controlador.restablecerEstadisticas();

        verify(estadisticaMock).reset();
        verify(estadisticaRepoMock).actualizar(estadisticaMock);
    }

    @Test
    void finalizarSesionCurso_deberiaActualizarOEliminarYFinalizar() throws Exception {
        controlador.iniciarCurso(curso, "Secuencial", "facil");
 
        controlador.finalizarSesionCurso();

        verify(estadisticaMock).finalizarSesion();
        verify(estadisticaRepoMock).actualizar(estadisticaMock);
    }

    @Test
    void eliminarCurso_deberiaLlamarAdaptador() {
        controlador.eliminarCurso(curso.getId());
        verify(cursoRepoMock).eliminarPorCursoId(curso.getId());
    }
    
    @Test
    void getProgreso_deberiaRetornarProgresoActual() throws Exception {
        controlador.iniciarCurso(curso, "Secuencial", "facil");
        assertEquals(0, controlador.getProgreso());
        controlador.avanzarProgreso();
        assertEquals(1, controlador.getProgreso());
    }
    
    @Test
    void getTotalPreguntas_deberiaRetornarTotalPreguntas() throws Exception {
        controlador.iniciarCurso(curso, "Secuencial", "facil");
        assertEquals(2, controlador.getTotalPreguntas());
    }

    @Test
    void getPorcentajeProgreso_deberiaCalcularCorrectamente() throws Exception {
        controlador.iniciarCurso(curso, "Secuencial", "facil");
        controlador.avanzarProgreso(); // progreso = 1, total = 2
        assertEquals(50, controlador.getPorcentajeProgreso());
    }
    
    @Test
    void restablecerCurso_deberiaResetearProgreso() throws Exception {
        controlador.iniciarCurso(curso, "Secuencial", "facil");
        controlador.avanzarProgreso();
        assertEquals(1, controlador.getProgreso());
        
        controlador.restablecerCurso();
        assertEquals(0, controlador.getProgreso());
        verify(cursoRepoMock, atLeastOnce()).actualizar(any());
    }

    @Test
    void importarCurso_lanzaExcepcionSiArchivoNoExiste() {
        assertThrows(IOException.class, () -> controlador.importarCurso("archivoInexistente.yaml"));
    }

    @Test
    void exportarEstadisticas_deberiaLlamarExportar() throws IOException {
        controlador.exportarEstadisticas("ruta.txt");
        verify(estadisticaMock).exportar("ruta.txt");
    }

    @Test
    void reanudarCurso_existenteDevuelveCursoYReconstruyeEstrategia() {
        CursoEnProgreso mockProgreso = mock(CursoEnProgreso.class);
        when(cursoRepoMock.buscarPorCursoId(curso.getId())).thenReturn(mockProgreso);
        when(mockProgreso.getDificultad()).thenReturn(Dificultad.FACIL);

        CursoEnProgreso result = controlador.reanudarCurso(curso);
        assertNotNull(result);
        verify(mockProgreso).reconstruirEstrategia();
    }

}
