package dominio;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;

/**
 * Clase abstracta que representa una pregunta genérica dentro de un curso.
 * 
 * Soporta herencia en base de datos (tabla única) y
 * serialización/deserialización con distintos subtipos a través de anotaciones
 * JSON y JPA.
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({ @JsonSubTypes.Type(value = PreguntaTest.class, name = "PreguntaTest"),
		@JsonSubTypes.Type(value = PreguntaRellenarHueco.class, name = "PreguntaRellenarHueco"),
		@JsonSubTypes.Type(value = PreguntaRespuestaCorta.class, name = "PreguntaRespuestaCorta") })
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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

	/**
	 * Valida si la respuesta del usuario coincide con la respuesta correcta.
	 *
	 * @param respuestaUsuario Respuesta ingresada por el usuario
	 * @return true si es correcta, false si no
	 */
	public boolean validarRespuesta(String respuestaUsuario) {
		if (respuestaUsuario != null) {
			return respuestaCorrecta.equalsIgnoreCase(respuestaUsuario);
		} else {
			return false;
		}
	}

	// --- Getters y Setters ---

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
