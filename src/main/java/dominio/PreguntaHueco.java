package dominio;

import java.util.ArrayList;
import java.util.List;

public class PreguntaHueco extends Pregunta {
	
	private static final long serialVersionUID = 1L;
	private List<String> listaOpciones;

	public PreguntaHueco() {
		super();
		this.listaOpciones = new ArrayList<String>();
	}

	public PreguntaHueco(String enunciado, String respuestaCorrecta, List<String> listaOpciones_, Dificultad dificultad) {
		super(enunciado, respuestaCorrecta, dificultad);
		listaOpciones = listaOpciones_;
	}

	public List<String> getListaOpciones() {
		return listaOpciones;
	}
	
	public void setListaOpciones(List<String> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}
}
