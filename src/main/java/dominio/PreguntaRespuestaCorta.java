package dominio;

public class PreguntaRespuestaCorta extends Pregunta {

	private static final long serialVersionUID = 1L;

	public PreguntaRespuestaCorta() {
		super();
	}

	public PreguntaRespuestaCorta(String enunciado, String respuestaCorrecta, Dificultad dificultad) {
		super(enunciado, respuestaCorrecta, dificultad);
	}
}
