package dominio;

public class Secuencial implements Estrategia {

	private int totalPreguntas;

    public Secuencial(int totalPreguntas) {
        this.totalPreguntas = totalPreguntas;
    }
    @Override
    public int mostrarPregunta(int nPregunta) {
        return nPregunta;
    }
}
