package dominio;

/**
 * Estrategia de presentación secuencial para las preguntas de un curso.
 * 
 * Muestra las preguntas en el mismo orden en que fueron definidas.
 */
public class Secuencial extends Estrategia {


    public Secuencial(int totalPreguntas) {
        super(totalPreguntas);
    }

    /**
     * Devuelve el índice de la pregunta correspondiente al progreso actual.
     * 
     * @param nPregunta número de pregunta que se desea mostrar
     * @return índice de la pregunta si está en rango, -1 si no
     */
    @Override
    public int mostrarPregunta(int nPregunta) {
        return nPregunta < ordenPreguntas.size() ? nPregunta : -1;
    }

    /**
     * Construye el orden de preguntas secuencialmente del 0 al total-1.
     */
    @Override
    protected void construirOrden() {
        for (int i = 0; i < totalPreguntasCurso; i++) {
            ordenPreguntas.add(i);
        }
    }
}
