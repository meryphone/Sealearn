package controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dominio.Curso;
import dominio.CursoEnProgreso;
import dominio.Estadistica;
import dominio.Estrategia;
import dominio.Pregunta;
import persistencia.AdaptadorCursoEnProgresoJPA;
import persistencia.AdaptadorEstadisticaJPA;
import persistencia.ICursoEnProgreso;
import persistencia.IEstadistica;
import utils.CursoUtils;
import utils.EstrategiaFactory;

public class Controlador {

	private static Controlador controlador;
	private Estadistica estadistica;
	private CursoEnProgreso cursoEnProgresoActual;
	private List<Curso> cursosImportados;
	
	private ICursoEnProgreso adaptadorCursoEnProgreso;
	private IEstadistica adaptadorEstadistica;

	private Controlador() {
		estadistica = new Estadistica();	
		cursosImportados = CursoUtils.cargarTodosLosCursos();	
		adaptadorCursoEnProgreso = AdaptadorCursoEnProgresoJPA.getIntance();
		adaptadorEstadistica = AdaptadorEstadisticaJPA.getIntance();

	}

	public static Controlador getInstance() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	public CursoEnProgreso iniciarCurso(Curso cursoSeleccionado, String estrategia_, String dificultad)
			throws RuntimeException {
		try {

			System.out.println(dificultad);

			List<Pregunta> preguntasFiltradas = filtrarPorDificultad(cursoSeleccionado.getPreguntas(), dificultad);

			System.out.println(preguntasFiltradas.size());		

			Estrategia estrategiaSeleccionada = EstrategiaFactory.crearEstrategia(estrategia_,
					preguntasFiltradas.size());

			cursoEnProgresoActual = new CursoEnProgreso(cursoSeleccionado.getId(), estrategiaSeleccionada,
					preguntasFiltradas, dificultad);
			
			estadistica.registrarEstudioHoy();
			adaptadorCursoEnProgreso.guardar(cursoEnProgresoActual);
			System.out.println("He creado el curso");
			return cursoEnProgresoActual;

		} catch (Exception e) {
			throw new RuntimeException("Error al iniciar el curso: " + e.getMessage(), e);
		}
	}

	public List<Pregunta> filtrarPorDificultad(List<Pregunta> preguntas, String dificultad) {
		List<Pregunta> resultado = new ArrayList<>();
		for (Pregunta p : preguntas) {
			if (p.getDificultad().equalsIgnoreCase(dificultad)) {
				resultado.add(p);
			}
		}
		return resultado;
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
	
	public List<Curso> getCursos(){
		return cursosImportados;
	}

	public Estadistica getEstadistica() {
		return estadistica;
	}

	public void finalizarSesion() {
		estadistica.finalizarSesion();
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

	public void importarCurso(File archivo) throws IOException {
		Curso curso = CursoUtils.importarCursoDesdeArchivo(archivo);
	    cursosImportados.add(curso);
	}
	
	public void eliminarCurso(Curso curso) {
	    cursosImportados.remove(curso);
	    adaptadorCursoEnProgreso.eliminarPorCursoId(curso.getId());
	}


}
