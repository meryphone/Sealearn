package dominio;

import java.util.List;

public class CursoEnProgreso {
	
	private int progreso; // Guarda por en que pregunta del curso va el usuario
	private Estrategia estrategia;
	private List<Pregunta> preguntas;
	
	public static final int PROGRESO_INICIAL = 0;
	
	public CursoEnProgreso(Estrategia estrategia, List<Pregunta> preguntas) {
		super();
		this.progreso = PROGRESO_INICIAL;
		this.estrategia = estrategia;
		this.preguntas = preguntas;
	}
		
	public Pregunta getPreguntaActual() {
		return preguntas.get(estrategia.mostrarPregunta(progreso));
	}
	
	public void avanzarProgreso() {
		progreso++;
	}
	
	
	
	// Getter y Setters
	
	public int getProgreso() {
		return progreso;
	}
	public void setProgreso(int progreso) {
		this.progreso = progreso;
	}

	public Estrategia getEstrategia() {
		return estrategia;
	}
	public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntasFiltradas_) {
		this.preguntas = preguntasFiltradas_;
	}
	
	
	

}
