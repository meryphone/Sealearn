package dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "Preguntas")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pregunta",
					 discriminatorType = DiscriminatorType.STRING)
public abstract class Pregunta {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	private String enunciado;
	private String respuestaCorrecta;
	private String dificultad;
	
    public Pregunta(String enunciado, String respuestaCorrecta, String dificultad) {
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.dificultad = dificultad;
    }
    
	public Pregunta() {
	}

    
    // Getters y Setters

	public boolean validarRespuesta(String respuestaUsuario) {
		return respuestaCorrecta.equalsIgnoreCase(respuestaUsuario);
	}

	// Getters y Setters
	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
