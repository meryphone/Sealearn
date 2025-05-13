package dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

/**
 * Representa una pregunta de respuesta corta donde el usuario debe escribir directamente la respuesta.
 */
@Entity
@DiscriminatorValue("RESPUESTA_CORTA")
public class PreguntaRespuestaCorta extends Pregunta {

    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * Constructor por defecto.
     */
    public PreguntaRespuestaCorta() {
        super();
    }

    /**
     * Constructor con parámetros.
     *
     * @param enunciado         Texto de la pregunta.
     * @param respuestaCorrecta Respuesta esperada.
     * @param dificultad        Nivel de dificultad de la pregunta.
     */
    public PreguntaRespuestaCorta(String enunciado, String respuestaCorrecta, Dificultad dificultad) {
        super(enunciado, respuestaCorrecta, dificultad);
    }
}
