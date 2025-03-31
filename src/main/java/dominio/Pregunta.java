package dominio;

public abstract class Pregunta {
	
    private String enunciado;
    private String respuestaCorrecta;
    protected String dificultad; // "facil", "media", "dificil"


    public Pregunta(String enunciado, String respuestaCorrecta, String dificultad) {
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.dificultad = dificultad;
    }
    
    public Pregunta() {}

    // Getters y Setters
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(String respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    
    public boolean validarRespuesta(String respuestaUsuario) {
    	return respuestaCorrecta.equalsIgnoreCase(respuestaUsuario);
    }
    
}
