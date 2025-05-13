package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que define la interfaz comun para todas las estrategias de
 * presentacion de preguntas en un curso.
 * 
 * Las subclases deben implementar la loica especi­fica para decidir que
 * pregunta mostrar en cada paso del curso.
 */
public abstract class Estrategia {

	protected int totalPreguntasCurso;
	protected List<Integer> ordenPreguntas;

	public Estrategia(int nPreguntas) {
		totalPreguntasCurso = nPreguntas;
		ordenPreguntas = new ArrayList<>();
		construirOrden();
	}		

	/**
	 * Devuelve el indice de la pregunta a mostrar en un paso determinado.
	 * 
	 * @param nPregunta Numero de paso actual del curso (0, 1, 2, ...)
	 * @return Indice de la pregunta en la lista original, o -1 si ya no hay mas
	 */
	public abstract int mostrarPregunta(int nPregunta);
	
	protected abstract void construirOrden();

	public void setTotalPreguntas(int totalPreguntas) {
		this.totalPreguntasCurso = totalPreguntas;
	}
	
	public int getTotalPreguntas() {
		return totalPreguntasCurso;
	}

}
