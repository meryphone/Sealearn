package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Estrategia de repetición espaciada. Muestra todas las preguntas una vez y
 * luego las va repitiendo, asegurando que cada una se repita al menos una vez.
 */
public class RepeticionEspaciada extends Estrategia {

	private List<Integer> ordenPreguntas;

	public RepeticionEspaciada() {
		ordenPreguntas = new ArrayList<>();
	}

	public RepeticionEspaciada(int totalPreguntas) {
		super(totalPreguntas);
		ordenPreguntas = new ArrayList<>();
	}

	@Override
	public int mostrarPregunta(int nPregunta) {
		if (ordenPreguntas.isEmpty()) {
			construirOrden();
		}

		if (nPregunta < ordenPreguntas.size()) {
			return ordenPreguntas.get(nPregunta);
		}
		return -1;
	}

	/**
	 * Construye la lista de orden con lógica de repetición espaciada. Cada pregunta
	 * se repite al menos una vez.
	 */
	private void construirOrden() {
		List<Integer> originales = new ArrayList<>();
		List<Integer> repetidas = new ArrayList<>();

		for (int i = 0; i < totalPreguntas; i++) {
			ordenPreguntas.add(i); // añadir pregunta nueva
			originales.add(i); // registrar original

			if ((i + 1) % 3 == 0 && !repetidas.contains(originales.get(0))) {
				ordenPreguntas.add(originales.get(0)); // repetir la más antigua no repetida
				repetidas.add(originales.remove(0)); // mover a repetidas
			}
		}

		// Asegurar que todas se repiten al menos una vez
		for (Integer faltante : originales) {
			ordenPreguntas.add(faltante);
		}
	}
}
