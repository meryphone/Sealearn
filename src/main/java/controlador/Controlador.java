package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import dominio.Curso;
import dominio.CursoEnProgreso;
import dominio.Dificultad;
import dominio.Estadistica;
import dominio.Pregunta;
import excepciones.CursoSinPreguntasCiertaDificultad;
import excepciones.ExcepcionCursoActualVacio;
import excepciones.ExcepcionCursoDuplicado;
import persistencia.AdaptadorCursoEnProgresoJPA;
import persistencia.AdaptadorEstadisticaJPA;
import persistencia.ICursoEnProgreso;
import persistencia.IEstadistica;
import utils.CursoUtils;

public class Controlador {

	private static Controlador controlador;
	private Estadistica estadistica;
	private CursoEnProgreso cursoEnProgresoActual;

	private ICursoEnProgreso adaptadorCursoEnProgreso;
	private IEstadistica adaptadorEstadistica;

	private Controlador() {
		adaptadorCursoEnProgreso = AdaptadorCursoEnProgresoJPA.getIntance();
		adaptadorEstadistica = AdaptadorEstadisticaJPA.getIntance();
		List<Estadistica> stats = adaptadorEstadistica.buscarTodos();
		if (stats.isEmpty()) {
			estadistica = new Estadistica();
		} else {
			estadistica = stats.getLast();
		}

	}

	public static Controlador getInstance() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	public CursoEnProgreso iniciarCurso(Curso cursoSeleccionado, String estrategia_, String dif)
			throws CursoSinPreguntasCiertaDificultad, ExcepcionCursoActualVacio {

		if (cursoSeleccionado != null) {

			Dificultad dificultad = Dificultad.valueOf(dif.toUpperCase());

			List<Pregunta> preguntasFiltradas = new ArrayList<Pregunta>();
			preguntasFiltradas = filtrarPorDificultad(cursoSeleccionado.getPreguntas(), dificultad);

			if (preguntasFiltradas.isEmpty()) {
				throw new CursoSinPreguntasCiertaDificultad("No hay preguntas para la dificultad seleccionada.");
			}

			cursoEnProgresoActual = new CursoEnProgreso(cursoSeleccionado.getId(), estrategia_, preguntasFiltradas,
					dificultad);

			estadistica.registrarEstudioHoy();
			adaptadorCursoEnProgreso.guardar(cursoEnProgresoActual);

			return cursoEnProgresoActual;

		} else {
			throw new ExcepcionCursoActualVacio("Seleccione un curso antes de comenzar");
		}

	}

	public List<Pregunta> filtrarPorDificultad(List<Pregunta> preguntas, Dificultad dificultad) {
		return preguntas.stream().filter((p) -> p.getDificultad() == dificultad).collect(Collectors.toList());
	}

	public boolean corregir(String respuesta) {
		Pregunta actual = cursoEnProgresoActual.getPreguntaActual();
		boolean acierto = actual.validarRespuesta(respuesta);
		estadistica.registrarRespuesta(acierto);
		return acierto;
	}

	public void avanzarProgreso() {
		cursoEnProgresoActual.avanzar();
		adaptadorEstadistica.actualizar(estadistica);
		adaptadorCursoEnProgreso.actualizar(cursoEnProgresoActual);
	}

	public CursoEnProgreso reanudarCurso(Curso cursoSeleccionado) {
		CursoEnProgreso cursoEnprogreso = adaptadorCursoEnProgreso.buscarPorCursoId(cursoSeleccionado.getId());
		if (cursoEnprogreso != null) {
			cursoEnprogreso.setPreguntas(
					filtrarPorDificultad(cursoSeleccionado.getPreguntas(), cursoEnprogreso.getDificultad()));
			cursoEnprogreso.reconstruirEstrategia();
			cursoEnProgresoActual = cursoEnprogreso;

		}
		return cursoEnprogreso;
	}

	public Estadistica getEstadistica() {
		return estadistica;
	}

	public int getProgreso() {
		return cursoEnProgresoActual != null ? cursoEnProgresoActual.getProgreso() : 0;
	}

	public int getTotalPreguntas() {
		return cursoEnProgresoActual != null ? cursoEnProgresoActual.getTotalPreguntas() : 0;
	}
	
	public void restablecerEstadisticas() {
		estadistica.reset();
		adaptadorEstadistica.actualizar(estadistica);
	}

	public int getPorcentajeProgreso() {
		return (int) ((cursoEnProgresoActual.getProgreso() * 100.0f) / cursoEnProgresoActual.getTotalPreguntas());
	}

	public CursoEnProgreso finalizarSesionCurso() {
		if (cursoEnProgresoActual != null && cursoEnProgresoActual.isCompletado()) {
			adaptadorCursoEnProgreso.eliminar(cursoEnProgresoActual);
		} else if (cursoEnProgresoActual != null && !cursoEnProgresoActual.isCompletado()) {
			adaptadorCursoEnProgreso.actualizar(cursoEnProgresoActual);
		}

		if (estadistica != null) {
			estadistica.finalizarSesion();
			adaptadorEstadistica.actualizar(estadistica);
		}

		cursoEnProgresoActual = null;
		return cursoEnProgresoActual;
	}

	public CursoEnProgreso restablecerCurso() {
		cursoEnProgresoActual.setProgreso(CursoEnProgreso.PROGRESO_INICIAL);
		adaptadorCursoEnProgreso.actualizar(cursoEnProgresoActual);
		return cursoEnProgresoActual;
	}

	public Curso importarCurso(String archivo) throws IOException, ExcepcionCursoDuplicado {
		return CursoUtils.importarCurso(archivo);
	}

	public void exportarEstadisticas(String rutaArchivo) throws IOException {
		estadistica.exportar(rutaArchivo);
	}

	public void eliminarCurso(Curso curso) {
		adaptadorCursoEnProgreso.eliminarPorCursoId(curso.getId());
	}

}
