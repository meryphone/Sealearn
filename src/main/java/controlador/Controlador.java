package controlador;

import java.util.List;
import java.util.stream.Collectors;
import dominio.Curso;
import dominio.CursoEnProgreso;
import dominio.Dificultad;
import dominio.Estadistica;
import dominio.Estrategia;
import dominio.Pregunta;
import persistencia.AdaptadorCursoEnProgresoJPA;
import persistencia.AdaptadorEstadisticaJPA;
import persistencia.ICursoEnProgreso;
import persistencia.IEstadistica;
import utils.EstrategiaFactory;

public class Controlador {

	private static Controlador controlador;
	private Estadistica estadistica;
	private CursoEnProgreso cursoEnProgresoActual;
	
	private ICursoEnProgreso adaptadorCursoEnProgreso;
	private IEstadistica adaptadorEstadistica;

	private Controlador() {
		estadistica = new Estadistica();	
		adaptadorCursoEnProgreso = AdaptadorCursoEnProgresoJPA.getIntance();
		adaptadorEstadistica = AdaptadorEstadisticaJPA.getIntance();

	}

	public static Controlador getInstance() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	public CursoEnProgreso iniciarCurso(Curso cursoSeleccionado, String estrategia_, String dif)
			throws RuntimeException {
		try {

			Dificultad dificultad = Dificultad.valueOf(dif.toUpperCase());

			List<Pregunta> preguntasFiltradas = filtrarPorDificultad(cursoSeleccionado.getPreguntas(), dificultad);	

			Estrategia estra = EstrategiaFactory.crearEstrategia(estrategia_, preguntasFiltradas.size());
			
			cursoEnProgresoActual = new CursoEnProgreso(cursoSeleccionado.getId(), estra,
					preguntasFiltradas, dificultad);
			
			estadistica.registrarEstudioHoy();
			adaptadorCursoEnProgreso.guardar(cursoEnProgresoActual);
			return cursoEnProgresoActual;

		} catch (Exception e) {
			throw new RuntimeException("Error al iniciar el curso: " + e.getMessage(), e);
		}
	}

	public List<Pregunta> filtrarPorDificultad(List<Pregunta> preguntas, Dificultad dificultad) {		
		return preguntas.stream()
				.filter((p)-> p.getDificultad() == dificultad)
				.collect(Collectors.toList());
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
			cursoEnprogreso.setPreguntas(filtrarPorDificultad(cursoSeleccionado.getPreguntas(), cursoEnprogreso.getDificultad()));
			cursoEnprogreso.reconstruirEstrategia();
			cursoEnProgresoActual = cursoEnprogreso;

		}
		return cursoEnprogreso;
	}

	public int getProgreso() {
		return cursoEnProgresoActual != null ? cursoEnProgresoActual.getProgreso() : 0;
	}

	public int getTotalPreguntas() {
		return cursoEnProgresoActual != null ? cursoEnProgresoActual.getTotalPreguntas() : 0;
	}
	

	public Estadistica getEstadistica() {
		return estadistica;
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

}
