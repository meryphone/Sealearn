package controladorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

	@BeforeEach
	void setUp() {
		estadisticaMock = mock(Estadistica.class);
		cursoRepoMock = mock(ICursoEnProgreso.class);
		estadisticaRepoMock = mock(IEstadistica.class);
		repoCurso = mock(RepositorioCursos.class);
		cursoEnProgresoMock = mock(CursoEnProgreso.class);
		curso = mock(Curso.class);

		List<Pregunta> preguntas = List.of(new PreguntaTest("¿2+2?", "4", List.of("3", "4", "5"), Dificultad.FACIL),
				new PreguntaTest("¿5+5?", "10", List.of("10", "11", "12"), Dificultad.FACIL));

		curso = new Curso("Curso Test", "Descripción", new ArrayList<>(preguntas));

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
		when(curso.getId()).thenReturn(java.util.UUID.randomUUID());
		when(curso.getPreguntas()).thenReturn(List.of());
		when(cursoRepoMock.buscarPorCursoId(any())).thenReturn(cursoEnProgresoMock);

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

}
