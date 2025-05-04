package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que define la interfaz comÃºn para todas las estrategias de
 * presentaciÃ³n de preguntas en un curso.
 * 
 * Las subclases deben implementar la lÃ³gica especÃ­fica para decidir quÃ©
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
	 * Devuelve el Ã­ndice de la pregunta a mostrar en un paso determinado.
	 * 
	 * @param nPregunta NÃºmero de paso actual del curso (0, 1, 2, ...)
	 * @return Ã�ndice de la pregunta en la lista original, o -1 si ya no hay mÃ¡s
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
