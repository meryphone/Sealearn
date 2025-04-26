package dominio;

import java.util.ArrayList;
import java.util.List;


public class PreguntaRellenarHueco extends Pregunta {

    private List<String> listaOpciones;

    public PreguntaRellenarHueco() {
        super();
        this.listaOpciones = new ArrayList<>();
    }

    public PreguntaRellenarHueco(String enunciado, String respuestaCorrecta, List<String> listaOpciones, String dificultad) {
        super(enunciado, respuestaCorrecta, dificultad);
        this.listaOpciones = (listaOpciones != null) ? listaOpciones : new ArrayList<>();
    }

    public List<String> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<String> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }
}
