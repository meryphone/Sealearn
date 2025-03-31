package dominio;

import java.util.List;

public class Curso {

    private String nombre;
    private String descripcion;
    private List<Pregunta> preguntas;
    
    public Curso() {};

    public Curso(String nombre, String descripcion, List<Pregunta> preguntas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preguntas = preguntas;
    }
    
    

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Curso curso = (Curso) obj;

        return nombre != null ? nombre.equals(curso.nombre) : curso.nombre == null;
    }
    
    @Override
    public String toString() {
        return "Curso: " + nombre + "\n" +
               "Descripción: " + descripcion + "\n" +
               "Número de preguntas: " + (preguntas != null ? preguntas.size() : 0);
    }   

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

	// Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Pregunta> getPreguntas() { return preguntas; }
    public void setPreguntas(List<Pregunta> preguntas) { this.preguntas = preguntas; }
    
    

}
