package dominio;

/**
 * Clase abstracta que define la interfaz común para todas las estrategias
 * de presentación de preguntas en un curso.
 * 
 * Las subclases deben implementar la lógica específica para decidir qué pregunta mostrar
 * en cada paso del curso.
 */
public abstract class Estrategia {
	
	int totalPreguntas;	

	/**
	 * Constructor por defecto.
	 */
	public Estrategia() {
		super();
	}
	
	public Estrategia(int nPreguntas) {
		super();
		totalPreguntas = nPreguntas;
	}
	
	/**
	 * Devuelve el índice de la pregunta a mostrar en un paso determinado.
	 * 
	 * @param nPregunta Número de paso actual del curso (0, 1, 2, ...)
	 * @return Índice de la pregunta en la lista original, o -1 si ya no hay más
	 */
	public abstract int mostrarPregunta(int nPregunta);

	/**
	 * Establece el número total de preguntas disponibles.
	 * Este valor debe configurarse antes de usar la estrategia.
	 * 
	 * @param nPreguntas Total de preguntas del curso
	 */
	public void setTotalPreguntas(int totalPreguntas){
		this.totalPreguntas = totalPreguntas;
	}
}
