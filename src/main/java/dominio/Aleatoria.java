package dominio;

import java.util.Collections;

/**
 * Estrategia de aprendizaje que muestra las preguntas en orden aleatorio,
 * sin repeticiones.
 */
public class Aleatoria extends Estrategia {
    
    public Aleatoria(int totalPreguntas) {
    	super(totalPreguntas);
    }

    @Override
    public int mostrarPregunta(int nPregunta) {

        if (nPregunta < ordenPreguntas.size()) {
            return ordenPreguntas.get(nPregunta);
        }
        return -1;
    }

    /**
     * Genera un orden aleatorio único para las preguntas del curso.
     */
    
    @Override
    protected void construirOrden() {

        for (int i = 0; i < totalPreguntasCurso; i++) {
            ordenPreguntas.add(i);
        }
        Collections.shuffle(ordenPreguntas);
    }
}
