package dominio;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cursos")
public class Curso {
	
	@Id
	private UUID id;
    private String nombre;
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_id")
    private List<Pregunta> preguntas;
    
    @JsonCreator
    public Curso(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("descripcion") String descripcion,
            @JsonProperty("preguntas") List<Pregunta> preguntas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preguntas = preguntas;
        this.id = generarUUIDDeterminista();  
    }
    
    public Curso() {
    	
    }
    
    private UUID generarUUIDDeterminista(Curso this) {
        String representacionUnica = this.toString();       
        return UUID.nameUUIDFromBytes(representacionUnica.getBytes());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Curso curso = (Curso) obj;

        return id != null ? id.equals(curso.id) : curso.id == null;
    }
    
    @Override
    public String toString() {
        return "Curso: " + nombre + "\n" +
               "Descripción: " + descripcion + "\n" +
               "Número de preguntas: " + (preguntas != null ? preguntas.size() : 0) + "\n" ;
    }   

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

	// Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Pregunta> getPreguntas() { return 	Collections.unmodifiableList(preguntas); }
    public void setPreguntas(List<Pregunta> preguntas) { this.preguntas = preguntas; }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
    
    
    

}