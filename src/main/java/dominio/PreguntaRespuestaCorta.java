package dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RESPUESTA_CORTA")
public class PreguntaRespuestaCorta extends Pregunta {

    public PreguntaRespuestaCorta() {
        super();
    }

    public PreguntaRespuestaCorta(String enunciado, String respuestaCorrecta, String dificultad) {
        super(enunciado, respuestaCorrecta, dificultad);
    }
}
