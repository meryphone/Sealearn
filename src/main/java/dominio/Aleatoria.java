package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Estrategia de aprendizaje que muestra las preguntas en orden aleatorio,
 * sin repeticiones.
 */
public class Aleatoria extends Estrategia {

    private List<Integer> ordenPreguntas;

    /**
     * Constructor que genera una lista de índices desordenada aleatoriamente.
     *
     * @param totalPreguntas número total de preguntas del curso
     */
    
    public Aleatoria(int totalPreguntas) {
    	super(totalPreguntas);
        ordenPreguntas = new ArrayList<>();
        for (int i = 0; i < this.totalPreguntas; i++) {
            ordenPreguntas.add(i); 
        }
        Collections.shuffle(ordenPreguntas); 
    }
    
    public Aleatoria() {
        ordenPreguntas = new ArrayList<>();
        for (int i = 0; i < totalPreguntas; i++) {
            ordenPreguntas.add(i); 
        }
        Collections.shuffle(ordenPreguntas); 
    }

    /**
     * Devuelve el índice de la pregunta a mostrar en una posición concreta del curso.
     *
     * @param nPregunta número de paso actual (0, 1, 2, ...)
     * @return índice aleatorio de la pregunta original, o -1 si se han mostrado todas
     */
    @Override
    public int mostrarPregunta(int nPregunta) {
        if (nPregunta < ordenPreguntas.size()) {
            return ordenPreguntas.get(nPregunta);
        } else {
            return -1; // Ya no hay más preguntas disponibles
        }
    }
}
