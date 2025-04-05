package controlador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import dominio.*;

public class Controlador {

	private static Controlador instancia;

	private List<Curso> listaCursos;
	private CursoEnProgreso cursoEnProgreso;
	private Curso cursoActual;
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

	// -------------------------------------------------
	// Gestión de cursos
	// -------------------------------------------------

	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public void recargarCursos() {
		listaCursos = CursoUtils.cargarTodosLosCursos();
	}

	public Curso getCursoActual() {
		return cursoActual;
	}

	public void setCursoActual(Curso curso) {
		this.cursoActual = curso;
	}

	// -------------------------------------------------
	// Inicialización y progreso de curso
	// -------------------------------------------------

	public void iniciarCurso(Curso cursoSeleccionado, String nombreEstrategia, String dificultad) {
		if (cursoSeleccionado == null || nombreEstrategia == null || dificultad == null) {
			System.err.println("Error: argumentos nulos en iniciarCurso");
			return;
		}

		List<Pregunta> preguntasFiltradas = filtrarPorDificultad(cursoSeleccionado.getPreguntas(), dificultad);
		if (preguntasFiltradas == null || preguntasFiltradas.isEmpty()) {
			System.err.println("No hay preguntas para la dificultad seleccionada.");
			return;
		}

		Estrategia estrategiaSeleccionada = crearEstrategia(nombreEstrategia, preguntasFiltradas.size());
		if (estrategiaSeleccionada == null) {
			System.err.println("No se pudo crear una estrategia.");
			return;
		}

		Curso cursoFiltrado = new Curso(cursoSeleccionado.getNombre(), cursoSeleccionado.getDescripcion(), preguntasFiltradas);
		cursoEnProgreso = new CursoEnProgreso(0, cursoFiltrado, estrategiaSeleccionada);
		cursoEnProgreso.setPreguntasFiltradas(preguntasFiltradas);

		estadistica.registrarEstudioHoy();
	}

	public void avanzarPregunta() {
		if (cursoEnProgreso != null) {
			cursoEnProgreso.setProgreso(cursoEnProgreso.getProgreso() + 1);
		}
	}
	
	public boolean hayMasPreguntas() {
		if (cursoEnProgreso == null) return false;
		int siguiente = cursoEnProgreso.getProgreso() + 1;
		return siguiente < cursoEnProgreso.getPreguntasFiltradas().size();
	}

	public int getProgreso() {
		return cursoEnProgreso != null ? cursoEnProgreso.getProgreso() : 0;
	}

	// -------------------------------------------------
	// Gestión de preguntas
	// -------------------------------------------------

	public Pregunta getPreguntaActual() {
		if (cursoEnProgreso == null || cursoEnProgreso.getCurso() == null || cursoEnProgreso.getEstrategia() == null) {
			return null;
		}
		List<Pregunta> preguntas = cursoEnProgreso.getPreguntasFiltradas();
		if (preguntas == null || preguntas.isEmpty()) return null;

		int index = cursoEnProgreso.getEstrategia().mostrarPregunta(cursoEnProgreso.getProgreso());
		return index < preguntas.size() ? preguntas.get(index) : null;
	}

	public Pregunta getPrimeraPregunta() {
		if (cursoEnProgreso == null || cursoEnProgreso.getCurso() == null || cursoEnProgreso.getEstrategia() == null) {
			return null;
		}

		List<Pregunta> preguntas = cursoEnProgreso.getPreguntasFiltradas();
		if (preguntas == null || preguntas.isEmpty()) return null;

		int index = cursoEnProgreso.getEstrategia().mostrarPregunta(0);
		return index < preguntas.size() ? preguntas.get(index) : null;
	}

	public boolean validarRespuesta(String respuesta) {
		Pregunta actual = getPreguntaActual();
		boolean acierto = actual != null && actual.validarRespuesta(respuesta);
		estadistica.registrarRespuesta(acierto);
		return acierto;
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


	// -------------------------------------------------
	// Estadísticas
	// -------------------------------------------------

	public Estadistica getEstadistica() {
		return estadistica;
	}

	public void exportarEstadisticas(String rutaArchivo) {
		try {
			estadistica.exportar(rutaArchivo);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al exportar estadísticas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void finalizarSesion() {
		estadistica.finalizarSesion();
	}

	// -------------------------------------------------
	// Estrategia
	// -------------------------------------------------

	private Estrategia crearEstrategia(String nombreEstrategia, int totalPreguntas) {
		if (nombreEstrategia == null) return null;

		switch (nombreEstrategia.trim().toLowerCase()) {
			case "secuencial":
				return new Secuencial(totalPreguntas);
			case "aleatoria":
				return new Aleatoria(totalPreguntas);
			case "repeticion espaciada":
			case "repetición espaciada":
				return new RepeticionEspaciada(totalPreguntas);
			default:
				System.err.println("Estrategia no reconocida: " + nombreEstrategia);
				return null;
		}
	}
}
