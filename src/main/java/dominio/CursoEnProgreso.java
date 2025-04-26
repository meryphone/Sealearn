package dominio;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import utils.EstrategiaFactory;

@Entity
@Table(name = "CursosEnProceso")
public class CursoEnProgreso {

	@Id
	private UUID cursoId;
	@Transient
	private Estrategia estrategia;
	@Transient
	private List<Pregunta> preguntas;		
	private String dificultad;
	private int progreso;
	private String estrategiaNombre;

	public static final int PROGRESO_INICIAL = 0;

	public CursoEnProgreso() {
	}

	public CursoEnProgreso(UUID cursoId, Estrategia estrategia, List<Pregunta> preguntas, String dificultad) {
		this.progreso = PROGRESO_INICIAL;
		this.cursoId = cursoId;
		this.estrategia = estrategia;
		this.estrategiaNombre = estrategia.getClass().getSimpleName();
		this.preguntas = preguntas;
		this.dificultad = dificultad;
	}

	public void reconstruirEstrategia() {
		this.estrategia = EstrategiaFactory.crearEstrategia(estrategiaNombre, preguntas.size());		
	}

	public Pregunta getPreguntaActual() {
		int indice = estrategia.mostrarPregunta(progreso);
		if (indice >= 0 && indice < preguntas.size()) {
			return preguntas.get(indice);
		}
		return null;
	}

	public void avanzar() {
		progreso++;
	}

	// Getters y Setters


	public UUID getCursoId() {
		return cursoId;
	}

	public int getProgreso() {
		return progreso;
	}

	public void setProgreso(int progreso) {
		this.progreso = progreso;
	}

	public Estrategia getEstrategia() {
		return estrategia;
	}

	public int getTotalPreguntas() {
		return estrategia.getTotalPreguntas();
	}


	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public String getDificultad() {
		return dificultad;
	}

	public boolean isCompletado() {
		return progreso == getTotalPreguntas();
	}

}
