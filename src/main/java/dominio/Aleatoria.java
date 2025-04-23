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

    public Aleatoria() {
    	ordenPreguntas = new ArrayList<>();
    }
    
    public Aleatoria(int totalPreguntas) {
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
     * Genera un orden aleatorio único para las preguntas del curso.
     */
    private void construirOrden() {
        for (int i = 0; i < totalPreguntas; i++) {
            ordenPreguntas.add(i);
        }
        Collections.shuffle(ordenPreguntas);
    }
}
