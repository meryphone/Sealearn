package dominio;

public class Secuencial extends Estrategia {
	
	public Secuencial() {
		super();
	}
	
	public Secuencial(int totalPreguntas) {
       super(totalPreguntas);
    }

	@Override
	public int mostrarPregunta(int nPregunta) {
		System.out.println(nPregunta);
		System.out.println(totalPreguntas);
		return nPregunta < totalPreguntas ? nPregunta : -1;
	}

}

