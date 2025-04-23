package dominio;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import utils.EstrategiaFactory;

@Entity
@Table(name = "CursosEnProceso")
public class CursoEnProgreso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID cursoId;

    private int progreso;

    private String estrategiaNombre; // ← persistido

    @Transient
    private Estrategia estrategia; // ← no se guarda

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "curso_en_progreso_id")
    private List<Pregunta> preguntas;

    public static final int PROGRESO_INICIAL = 0;

    public CursoEnProgreso() {}

    public CursoEnProgreso(UUID cursoId, Estrategia estrategia, List<Pregunta> preguntas) {
        this.progreso = PROGRESO_INICIAL;
        this.cursoId = cursoId;
        this.estrategia = estrategia;
        this.estrategiaNombre = estrategia.getClass().getSimpleName(); // Ej: "Aleatoria"
        this.preguntas = preguntas;
    }

    public void reconstruirEstrategia() { // VER DONDE PONGO ESTE MÉTODO
        this.estrategia = EstrategiaFactory.crearEstrategia(estrategiaNombre, preguntas.size());
    }

    public Pregunta getPreguntaActual() {
        int indice = estrategia.mostrarPregunta(progreso);
        if (indice >= 0 && indice < preguntas.size()) {
            return preguntas.get(indice);
        }
        return null;
    }

    public void avanzarProgreso() {
        progreso++;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public Estrategia getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(Estrategia estrategia) {
        this.estrategia = estrategia;
        this.estrategiaNombre = estrategia.getClass().getSimpleName();
    }

    public String getEstrategiaNombre() {
        return estrategiaNombre;
    }

    public void setEstrategiaNombre(String estrategiaNombre) {
        this.estrategiaNombre = estrategiaNombre;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
