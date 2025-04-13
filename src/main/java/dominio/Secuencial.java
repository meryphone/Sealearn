package dominio;

public class Secuencial extends Estrategia {
	
	private int totalPreguntas;
	
	public Secuencial() {
		super();
	}
	
	public Secuencial(int totalPreguntas) {
       super(totalPreguntas);
    }

	@Override
	public int mostrarPregunta(int nPregunta) {
		return nPregunta < totalPreguntas ? nPregunta : -1;
	}

}

