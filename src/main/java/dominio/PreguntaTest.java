package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Transient;

/**
 * Representa una pregunta de tipo test con varias opciones posibles.
 * El usuario debe seleccionar la correcta entre ellas.
 */
@Entity
@DiscriminatorValue("TEST")
public class PreguntaTest extends Pregunta {

    @Transient
    private static final long serialVersionUID = 1L;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "opciones_test")
    @Column(name = "opcion")
    private List<String> listaOpciones;

    /**
     * Constructor por defecto. Inicializa la lista de opciones vacía.
     */
    public PreguntaTest() {
        super();
        this.listaOpciones = new ArrayList<>();
    }

    /**
     * Constructor con parámetros.
     *
     * @param enunciado         Texto de la pregunta.
     * @param respuestaCorrecta Respuesta correcta esperada.
     * @param listaOpciones     Lista de opciones posibles.
     * @param dificultad        Nivel de dificultad de la pregunta.
     */
    public PreguntaTest(String enunciado, String respuestaCorrecta, List<String> listaOpciones, Dificultad dificultad) {
        super(enunciado, respuestaCorrecta, dificultad);
        this.listaOpciones = (listaOpciones != null) ? listaOpciones : new ArrayList<>();
    }

    public List<String> getListaOpciones() {
        return Collections.unmodifiableList(listaOpciones);
    }

    public void setListaOpciones(List<String> listaOpciones) {
        this.listaOpciones = new ArrayList<>(listaOpciones);
    }
}
