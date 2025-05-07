package dominio;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@DiscriminatorValue("RESPUESTA_CORTA")
public class PreguntaRespuestaCorta extends Pregunta {
	@Transient
	private static final long serialVersionUID = 1L;

	public PreguntaRespuestaCorta() {
		super();
	}

	public PreguntaRespuestaCorta(String enunciado, String respuestaCorrecta, Dificultad dificultad) {
		super(enunciado, respuestaCorrecta, dificultad);
	}
}
