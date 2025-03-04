package dominio;

import java.util.ArrayList;
import java.util.List;

public class PreguntaHueco extends Pregunta {
	
	private final List<String> listaOpciones;

	public PreguntaHueco() {
		super();
		this.listaOpciones = new ArrayList<String>();
	}

	public PreguntaHueco(String enunciado, String respuestaCorrecta, List<String> listaOpciones_) {
		super(enunciado, respuestaCorrecta);
		listaOpciones = listaOpciones_;
	}

	public List<String> getListaOpciones() {
		return listaOpciones;
	}
	

}
