package dominio;

/**
 * Clase abstracta que define la interfaz comÃºn para todas las estrategias de
 * presentaciÃ³n de preguntas en un curso.
 * 
 * Las subclases deben implementar la lÃ³gica especÃ­fica para decidir quÃ©
 * pregunta mostrar en cada paso del curso.
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
	 * Devuelve el Ã­ndice de la pregunta a mostrar en un paso determinado.
	 * 
	 * @param nPregunta NÃºmero de paso actual del curso (0, 1, 2, ...)
	 * @return Ã�ndice de la pregunta en la lista original, o -1 si ya no hay mÃ¡s
	 */
	public abstract int mostrarPregunta(int nPregunta);
	
	public int getTotalPreguntas() {
		return totalPreguntas;
	}

	/**
	 * Establece el nÃºmero total de preguntas disponibles. Este valor debe
	 * configurarse antes de usar la estrategia.
	 * 
	 * @param nPreguntas Total de preguntas del curso
	 */
	public void setTotalPreguntas(int totalPreguntas) {
		this.totalPreguntas = totalPreguntas;
	}
}
