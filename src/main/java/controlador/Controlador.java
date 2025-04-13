package controlador;


import java.util.ArrayList;
import java.util.List;
import dominio.*;
import utils.CursoUtils;


public class Controlador {

	private static Controlador controlador;
	private List<Curso> listaCursos;
	private Estadistica estadistica;
	private  CursoEnProgreso cursoEnProgreso;

	private Controlador() {
		listaCursos = CursoUtils.cargarTodosLosCursos();
		estadistica = new Estadistica();
	}

	public static synchronized Controlador getInstance() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}


	public List<Curso> getListaCursos() {
		return listaCursos;
	}	
	

	public CursoEnProgreso iniciarCurso(Curso cursoSeleccionado, String estrategia_, String dificultad) throws RuntimeException{
	    try {
	    	
	    	System.out.println(dificultad);
	    
	        List<Pregunta> preguntasFiltradas = filtrarPorDificultad(cursoSeleccionado.getPreguntas(), dificultad);
	        
	        System.out.println(preguntasFiltradas.size());
	        
	        if (preguntasFiltradas == null || preguntasFiltradas.isEmpty()) {
	            throw new IllegalStateException("No hay preguntas disponibles para la dificultad: " + dificultad);
	        }
	        
	        Estrategia estrategiaSeleccionada = crearEstrategia(estrategia_);
	        estrategiaSeleccionada.setTotalPreguntas(preguntasFiltradas.size());
	        
	        cursoEnProgreso = new CursoEnProgreso(
	            estrategiaSeleccionada,
	            preguntasFiltradas
	        );
	        
	     // Aquí hacer cosas de persistencia

	        estadistica.registrarEstudioHoy();
	        
	        return cursoEnProgreso;

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
		Pregunta actual = cursoEnProgreso.getPreguntaActual();
		boolean acierto = actual.validarRespuesta(respuesta);
		estadistica.registrarRespuesta(acierto);

		return acierto;
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
	
	public void finalizarCurso() {
		// Aquí hacer cosas de persistencia
		cursoEnProgreso = null;
	}

	// Funciones Auxiliares

	private Estrategia crearEstrategia(String nombreEstrategia) {
		nombreEstrategia = nombreEstrategia.replace(" ", "");
		try {
			return (Estrategia) Class.forName("dominio." + nombreEstrategia).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
