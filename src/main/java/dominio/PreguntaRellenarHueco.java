package dominio;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("RELLENAR_HUECO")
public class PreguntaRellenarHueco extends Pregunta {

    @ElementCollection
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
