package dominio;

public class CursoEnProgreso {
	
	private int progreso;
	private Curso curso;
	private Estrategia estrategia;	
	
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
	
	
	

}
