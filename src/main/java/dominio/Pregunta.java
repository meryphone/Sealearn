package dominio;

public abstract class Pregunta {
	
    private String enunciado;
    private String respuestaCorrecta;


    public Pregunta(String enunciado, String respuestaCorrecta) {
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
    }
    
    public Pregunta() {}

    // Getters y Setters
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(String respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }
    
    public boolean validarRespuesta(String respuestaUsuario) {
    	return respuestaUsuario.equalsIgnoreCase(respuestaUsuario);
    }
    
}
