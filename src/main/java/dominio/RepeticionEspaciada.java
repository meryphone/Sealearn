package dominio;
import java.util.ArrayList;
import java.util.List;

/**
 * Estrategia de repetición espaciada. Muestra todas las preguntas una vez y
 * vuelve a repetirlas cada 3 preguntas nuevas. Trabaja con los índices
 * (enteros) de las preguntas.
 */

public class RepeticionEspaciada extends Estrategia {

	private List<Integer> ordenPreguntas; // Lista que define el orden completo de presentación de preguntas (incluyendo
											// repeticiones)
	private int totalPreguntas;

	/**
	 * Constructor que genera el orden de presentación de preguntas aplicando la
	 * lógica de repetición espaciada.
	 * 
	 * @param totalPreguntas Número total de preguntas originales (sin repetir)
	 */

	public RepeticionEspaciada(int totalPreguntas) {
		super(totalPreguntas);
		ordenPreguntas = new ArrayList<>();
		int repetirIndex = 0;
		List<Integer> preguntasMostradas = new ArrayList<>();


		for (int i = 0; i < this.totalPreguntas; i++) {
			ordenPreguntas.add(i);
			preguntasMostradas.add(i);

			if ((i + 1) % 3 == 0 && repetirIndex < preguntasMostradas.size()) {
				ordenPreguntas.add(preguntasMostradas.get(repetirIndex));
				repetirIndex++;
			}
		}
	}

	public RepeticionEspaciada() {
		ordenPreguntas = new ArrayList<>();
		int preguntaArepetir = 0;
		List<Integer> preguntasMostradas = new ArrayList<>(); // Registro de preguntas ya mostradas

		// Recorremos cada pregunta original
		for (int i = 0; i < totalPreguntas; i++) {
			ordenPreguntas.add(i);
			preguntasMostradas.add(i);

			// Cada 3 preguntas, repetimos una anterior (siguiendo el orden de aparición)
			if ((i + 1) % 3 == 0 && preguntaArepetir < preguntasMostradas.size()) {
				ordenPreguntas.add(preguntasMostradas.get(preguntaArepetir));
				preguntaArepetir++;
			}
		}
	}

	/**
	 * Devuelve el índice de la pregunta a mostrar en un paso concreto.
	 * 
	 * @param nPregunta Número de paso actual en el curso (0, 1, 2, ...)
	 * @return Índice de la pregunta en la lista original, o -1 si ya no hay más
	 *         preguntas
	 */
	@Override
	public int mostrarPregunta(int nPregunta) {
		if (nPregunta < ordenPreguntas.size()) {
			return ordenPreguntas.get(nPregunta);
		} else {
			return -1; // Ya no quedan preguntas por mostrar
		}
	}
}

