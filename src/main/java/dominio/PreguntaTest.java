package dominio;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TEST")
public class PreguntaTest extends Pregunta {

    @ElementCollection
    private List<String> listaOpciones;

    public PreguntaTest() {
        super();
        this.listaOpciones = new ArrayList<>();
    }

    public PreguntaTest(String enunciado, String respuestaCorrecta, List<String> listaOpciones, String dificultad) {
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
