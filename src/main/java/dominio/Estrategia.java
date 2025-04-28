package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que define la interfaz común para todas las estrategias de
 * presentación de preguntas en un curso.
 * 
 * Las subclases deben implementar la lógica específica para decidir qué
 * pregunta mostrar en cada paso del curso.
 */
public abstract class Estrategia {

	protected int totalPreguntasCurso;
	protected List<Integer> ordenPreguntas;

	/**
	 * Constructor por defecto.
	 */
	public Estrategia() {
		ordenPreguntas = new ArrayList<>();
	}

	public Estrategia(int nPreguntas) {
		totalPreguntasCurso = nPreguntas;
		ordenPreguntas = new ArrayList<>();
	}		

	/**
	 * Devuelve el índice de la pregunta a mostrar en un paso determinado.
	 * 
	 * @param nPregunta Número de paso actual del curso (0, 1, 2, ...)
	 * @return Índice de la pregunta en la lista original, o -1 si ya no hay más
	 */
	public abstract int mostrarPregunta(int nPregunta);
	
	protected abstract void construirOrden();

	public void setTotalPreguntas(int totalPreguntas) {
		this.totalPreguntasCurso = totalPreguntas;
	}
	
	public int getTotalPreguntas() {
		return ordenPreguntas.size();
	}

}
