package dominio;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;


@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "tipo"
	)
	@JsonSubTypes({
	    @JsonSubTypes.Type(value = PreguntaTest.class, name = "PreguntaTest"),
	    @JsonSubTypes.Type(value = PreguntaRellenarHueco.class, name = "PreguntaRellenarHueco"),
	    @JsonSubTypes.Type(value = PreguntaRespuestaCorta.class, name = "PreguntaRespuestaCorta")
	})

@Entity
@Inheritance(strategy =  InheritanceType.SINGLE_TABLE)
@Table(name = "Preguntas")
@DiscriminatorColumn(name = "tipo_pregunta", discriminatorType = DiscriminatorType.STRING)
public abstract class Pregunta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	private String enunciado;
	private String respuestaCorrecta;
	private Dificultad dificultad;

	public Pregunta(String enunciado, String respuestaCorrecta, Dificultad dificultad) {
		this.enunciado = enunciado;
		this.respuestaCorrecta = respuestaCorrecta;
		this.dificultad = dificultad;
	}

	public Pregunta() {
	}

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

	public Dificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}

}
