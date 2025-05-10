package controladorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controlador.Controlador;
import dominio.Curso;
import dominio.CursoEnProgreso;
import dominio.Dificultad;
import dominio.Estadistica;
import dominio.Pregunta;
import dominio.PreguntaTest;
import dominio.RepositorioCursos;
import excepciones.CursoSinPreguntasCiertaDificultad;
import excepciones.ExcepcionCursoActualVacio;
import persistencia.ICursoEnProgreso;
import persistencia.IEstadistica;

public class ControladorTest {

	private Controlador controlador;
	private Estadistica estadisticaMock;
	private RepositorioCursos repoCurso;
	private ICursoEnProgreso cursoRepoMock;
	private IEstadistica estadisticaRepoMock;
	private CursoEnProgreso cursoEnProgresoMock;
	private Curso curso;
	private Pregunta mockPregunta;

	@BeforeEach
	void setUp() {
	    estadisticaMock = mock(Estadistica.class);
	    cursoRepoMock = mock(ICursoEnProgreso.class);
	    estadisticaRepoMock = mock(IEstadistica.class);
	    repoCurso = mock(RepositorioCursos.class);
	    cursoEnProgresoMock = mock(CursoEnProgreso.class);
	    mockPregunta = mock(Pregunta.class);
	    
	    curso = mock(Curso.class);  
	    when(curso.getId()).thenReturn(UUID.randomUUID());
	    when(curso.getPreguntas()).thenReturn(List.of());

	    controlador = new Controlador(estadisticaMock, cursoRepoMock, estadisticaRepoMock, repoCurso);
	}

	@Test
	public void testCursoSeleccionadoEsNull_lanzaExcepcion() {
		assertThrows(ExcepcionCursoActualVacio.class, () -> {
			controlador.iniciarCurso(null, "Secuencial", "FACIL");
		});
	}

	@Test
	public void testCursoSinPreguntasFiltradas_lanzaExcepcion() {
		Curso cursoVacio = new Curso("C1", "desc", List.of());

		assertThrows(CursoSinPreguntasCiertaDificultad.class, () -> {
			controlador.iniciarCurso(cursoVacio, "Aleatoria", "MEDIA");
		});
	}

	@Test
	public void testCursoConPreguntasValidas_flujoCorrecto() throws Exception {
		Pregunta p1 = new PreguntaTest("¿2+2?", "4", List.of("3", "4", "5"), Dificultad.FACIL);
		Curso curso = new Curso("C2", "desc", List.of(p1));

		CursoEnProgreso result = controlador.iniciarCurso(curso, "RepeticionEspaciada", "facil");

		assertNotNull(result);
		assertEquals(curso.getId(), result.getCursoId());
		verify(estadisticaMock).registrarEstudioHoy();
		verify(cursoRepoMock).guardar(any(CursoEnProgreso.class));
	}

	@Test
	public void testDificultadNoValida_lanzaIllegalArgument() {
		Pregunta p1 = new PreguntaTest("Pregunta", "R", List.of("R"), Dificultad.FACIL);
		Curso curso = new Curso("C3", "desc", List.of(p1));

		assertThrows(IllegalArgumentException.class, () -> {
			controlador.iniciarCurso(curso, "Aleatoria", "INVALIDA");
		});
	}

	@Test
	public void testReanudarCurso_existente() {
	    when(cursoRepoMock.buscarPorCursoId(any(UUID.class))).thenReturn(cursoEnProgresoMock);

	    CursoEnProgreso result = controlador.reanudarCurso(curso);

	    assertEquals(cursoEnProgresoMock, result);
	    verify(cursoEnProgresoMock).setPreguntas(anyList());
	    verify(cursoEnProgresoMock).reconstruirEstrategia();
	}


	@Test
	public void testReanudarCurso_noExistente() {
		when(curso.getId()).thenReturn(java.util.UUID.randomUUID());
		when(cursoRepoMock.buscarPorCursoId(any())).thenReturn(null);

		CursoEnProgreso result = controlador.reanudarCurso(curso);

		assertNull(result);
	}

	@Test
	public void testRestablecerEstadisticas() {
		controlador.restablecerEstadisticas();
		verify(estadisticaMock).reset();
		verify(estadisticaRepoMock).actualizar(estadisticaMock);
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
	
	@Test
	public void testCorregir_respuestaCorrecta() {
	    when(mockPregunta.validarRespuesta("42")).thenReturn(true);
	    when(cursoEnProgresoMock.getPreguntaActual()).thenReturn(mockPregunta);

	    controlador.setCursoEnProgresoActual(cursoEnProgresoMock);  

	    boolean resultado = controlador.corregir("42");

	    assertTrue(resultado);
	    verify(mockPregunta).validarRespuesta("42");
	    verify(estadisticaMock).registrarRespuesta(true);
	}
	
	@Test
	void testAvanzarProgreso_realizaActualizaciones() {
	    controlador.setCursoEnProgresoActual(cursoEnProgresoMock);

	    controlador.avanzarProgreso();

	    verify(cursoEnProgresoMock).avanzar();
	    verify(estadisticaRepoMock).actualizar(estadisticaMock);
	    verify(cursoRepoMock).actualizar(cursoEnProgresoMock);
	}
	
	@Test
	void testFinalizarSesionCurso_completadoEliminaCurso() {
	    controlador.setCursoEnProgresoActual(cursoEnProgresoMock);
	    when(cursoEnProgresoMock.isCompletado()).thenReturn(true);

	    controlador.finalizarSesionCurso();

	    verify(cursoRepoMock).eliminar(cursoEnProgresoMock);
	    verify(estadisticaMock).finalizarSesion();
	    verify(estadisticaRepoMock).actualizar(estadisticaMock);
	}

	@Test
	void testRestablecerCurso_reiniciaProgreso() {
	    controlador.setCursoEnProgresoActual(cursoEnProgresoMock);

	    CursoEnProgreso result = controlador.restablecerCurso();

	    verify(cursoEnProgresoMock).setProgreso(CursoEnProgreso.PROGRESO_INICIAL);
	    verify(cursoRepoMock).actualizar(cursoEnProgresoMock);
	    assertEquals(cursoEnProgresoMock, result);
	}

	@Test
	void testGetPorcentajeProgreso_devuelveCorrecto() {
	    controlador.setCursoEnProgresoActual(cursoEnProgresoMock);
	    when(cursoEnProgresoMock.getProgreso()).thenReturn(5);
	    when(cursoEnProgresoMock.getTotalPreguntas()).thenReturn(10);

	    int porcentaje = controlador.getPorcentajeProgreso();

	    assertEquals(50, porcentaje);
	}

	@Test
	void testGetPorcentajeProgreso_conTotalPreguntasCero() {
	    controlador.setCursoEnProgresoActual(cursoEnProgresoMock);
	    when(cursoEnProgresoMock.getProgreso()).thenReturn(5);
	    when(cursoEnProgresoMock.getTotalPreguntas()).thenReturn(0);

	    int porcentaje = controlador.getPorcentajeProgreso();

	    assertEquals(0, porcentaje);
	}

}
