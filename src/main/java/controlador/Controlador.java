package controlador;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import dominio.*;
import vistas.*;

public class Controlador {

	private static Controlador instancia;
	private List<Curso> listaCursos;
	private CursoEnProgreso cursoEnProgreso;
	private Estadistica estadistica;

	private Controlador() {
		listaCursos = CursoUtils.cargarTodosLosCursos();
		estadistica = new Estadistica();
	}

	public static synchronized Controlador getInstance() {
		if (instancia == null) {
			instancia = new Controlador();
		}
		return instancia;
	}

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void iniciarCurso(Curso cursoSeleccionado, String estrategia_, String dificultad) {
		
		Estrategia estrategiaSeleccionada = crearEstrategia(estrategia_);
		
		CursoEnProgreso cursoEnProgreso = new CursoEnProgreso(CursoEnProgreso.PROGRESO_ESTANDAR,cursoSeleccionado, estrategiaSeleccionada);
				
		List<Pregunta> preguntasFiltradas = filtrarPorDificultad(cursoSeleccionado.getPreguntas(), dificultad);
		
		cursoEnProgreso.setPreguntasFiltradas(preguntasFiltradas);

		Curso cursoClonado = new Curso(cursoSeleccionado.getNombre(), cursoSeleccionado.getDescripcion(), preguntasFiltradas);
		cursoEnProgreso = new CursoEnProgreso(0, cursoClonado, estrategiaSeleccionada);
		estadistica.registrarEstudioHoy();
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

	public Pregunta getPreguntaActual() {
		int index = cursoEnProgreso.getEstrategia().mostrarPregunta(cursoEnProgreso.getProgreso());
		List<Pregunta> preguntas = cursoEnProgreso.getCurso().getPreguntas();
		return index < preguntas.size() ? preguntas.get(index) : null;
	}

	public void avanzarPregunta() {
		cursoEnProgreso.setProgreso(cursoEnProgreso.getProgreso() + 1);
	}

	public boolean validarRespuesta(String respuesta) {
		Pregunta actual = getPreguntaActual();
		boolean acierto = actual != null && actual.validarRespuesta(respuesta);
		estadistica.registrarRespuesta(acierto);
		return acierto;
	}

	public Curso getCursoActual() {
		return cursoEnProgreso != null ? cursoEnProgreso.getCurso() : null;
	}

	public int getProgreso() {
		return cursoEnProgreso != null ? cursoEnProgreso.getProgreso() : 0;
	}

	public Estadistica getEstadistica() {
		return estadistica;
	}

	public void finalizarSesion() {
		estadistica.finalizarSesion();
	}

	// Funciones Auxiliares

	private Estrategia crearEstrategia(String nombreEstrategia) {
		nombreEstrategia = nombreEstrategia.replace(" ", "");
		try {
			return (Estrategia) Class.forName(nombreEstrategia).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			System.exit(1);
		}
		return null;
	}
}
