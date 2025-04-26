package dominio;

import java.util.List;
import java.util.UUID;


public class Curso {
	
	private UUID id;
    private String nombre;
    private String descripcion;
    private List<Pregunta> preguntas;
    
    public Curso() {
    	this.id = UUID.randomUUID();
    };

    public Curso(String nombre, String descripcion, List<Pregunta> preguntas, int progreso) {
    	this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preguntas = preguntas;
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
               "Número de preguntas: " + (preguntas != null ? preguntas.size() : 0) + "\n" +
               "Id: " + id ;
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

    public List<Pregunta> getPreguntas() { return preguntas; }
    public void setPreguntas(List<Pregunta> preguntas) { this.preguntas = preguntas; }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
    
    
    

}