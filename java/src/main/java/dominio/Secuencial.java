package dominio;

public class Secuencial extends Estrategia {
	
	public Secuencial(int totalPreguntas) {
       super(totalPreguntas);
    }

	@Override
	public int mostrarPregunta(int nPregunta) {
		return nPregunta < ordenPreguntas.size() ? nPregunta : -1;
	}

	@Override
	protected void construirOrden() {
		for(int i = 0; i < totalPreguntasCurso; i++) {
			ordenPreguntas.add(i);
		}
	}
	
	

}