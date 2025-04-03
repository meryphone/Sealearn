package dominio;

import java.util.List;

public class CursoEnProgreso {

	private int progreso;
	private Curso curso;
	private Estrategia estrategia;
	private List<Pregunta> preguntasFiltradas;
	
	public static final int PROGRESO_ESTANDAR = 0;

	public CursoEnProgreso(int progreso, Curso curso, Estrategia estrategia) {
		super();
		this.progreso = progreso;
		this.curso = curso;
		this.estrategia = estrategia;
	}

	// Getter y Setters

	public int getProgreso() {
		return progreso;
	}

	public void setProgreso(int progreso) {
		this.progreso = progreso;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Estrategia getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}

	public List<Pregunta> getPreguntasFiltradas() {
		return preguntasFiltradas;
	}

	public void setPreguntasFiltradas(List<Pregunta> preguntasFiltradas_) {
		this.preguntasFiltradas = preguntasFiltradas_;
	}
	
	


}
