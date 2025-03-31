package dominio;

import java.util.ArrayList;
import java.util.List;

public class PreguntaTest extends Pregunta{
	
	private List<String> listaOpciones;
	
	public PreguntaTest(String enunciado, String respuestaCorrecta, List<String> listaOpciones_, String dificultad) {
		super(enunciado, respuestaCorrecta, dificultad);
		this.listaOpciones = listaOpciones_;
	}
	
	public PreguntaTest() {
		super();
		this.listaOpciones = new ArrayList<String>();
	}
	
	// Getter y Setters 
	public List<String> getListaOpciones() {
		return listaOpciones;
	}
	
	public void setListaOpciones(List<String> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}
}
