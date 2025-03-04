package dominio;

import java.util.List;

public class Curso {

    private String nombre;
    private String descripcion;
    private String estrategia;
    private String dificultad;
    private List<Pregunta> preguntas;
    private int progreso;
    
    public Curso() {};

    public Curso(String nombre, String descripcion, String estrategia, String dificultad, List<Pregunta> preguntas, int progreso) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estrategia = estrategia;
        this.dificultad = dificultad;
        this.preguntas = preguntas;
        this.progreso = progreso;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstrategia() { return estrategia; }
    public void setEstrategia(String estrategia) { this.estrategia = estrategia; }

    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }

    public List<Pregunta> getPreguntas() { return preguntas; }
    public void setPreguntas(List<Pregunta> preguntas) { this.preguntas = preguntas; }

    public int getProgreso() { return progreso; }
    public void setProgreso(int progreso) { this.progreso = progreso; }
}
