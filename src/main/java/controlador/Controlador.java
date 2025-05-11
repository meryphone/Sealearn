package controlador;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dominio.*;
import excepciones.*;
import persistencia.*;

/**
 * Controlador principal de la aplicación que gestiona la lógica entre los cursos,
 * estadísticas y el progreso de aprendizaje del usuario.
 * 
 * Aplica el patrón Singleton para mantener una única instancia durante la ejecución.
 */
public class Controlador {

    private static Controlador controlador;
    private Estadistica estadistica;
    private CursoEnProgreso cursoEnProgresoActual;
    private RepositorioCursos repoCursos;
    private ICursoEnProgreso adaptadorCursoEnProgreso;
    private IEstadistica adaptadorEstadistica;
    private ICurso adaptadorCurso;

    /**
     * Constructor privado que inicializa los adaptadores y la estadística del usuario.
     */
    private Controlador() {
        repoCursos = RepositorioCursos.getInstance();
        adaptadorCursoEnProgreso = AdaptadorCursoEnProgresoJPA.getIntance();
        adaptadorEstadistica = AdaptadorEstadisticaJPA.getIntance();
        adaptadorCurso = AdaptadorCursoJPA.getInstance();
        List<Estadistica> stats = adaptadorEstadistica.buscarTodos();
        this.estadistica = stats.isEmpty() ? new Estadistica() : stats.getLast();
    }

    /**
     * Constructor alternativo utilizado principalmente para pruebas unitarias.
     */
    public Controlador(Estadistica stats, ICursoEnProgreso cursoAdapter, IEstadistica estadisticaAdapter, RepositorioCursos repoCurso) {
        this.adaptadorCursoEnProgreso = cursoAdapter;
        this.adaptadorEstadistica = estadisticaAdapter;
        this.estadistica = stats;
        this.repoCursos = repoCurso;
    }

    /**
     * Obtiene la instancia singleton del controlador.
     * 
     * @return Instancia de Controlador
     */
    public static Controlador getInstance() {
        if (controlador == null) {
            controlador = new Controlador();
        }
        return controlador;
    }

    /**
     * Inicia un curso seleccionado aplicando la estrategia y dificultad dadas.
     * 
     * @param cursoSeleccionado Curso a iniciar
     * @param estrategia_ Estrategia de aprendizaje
     * @param dif Dificultad deseada
     * @return CursoEnProgreso generado
     * @throws CursoSinPreguntasCiertaDificultad Si no hay preguntas disponibles para esa dificultad
     * @throws ExcepcionCursoActualVacio Si no se ha seleccionado un curso
     */
    public CursoEnProgreso iniciarCurso(Curso cursoSeleccionado, String estrategia_, String dif)
            throws CursoSinPreguntasCiertaDificultad, ExcepcionCursoActualVacio {
        if (cursoSeleccionado != null) {
            Dificultad dificultad = Dificultad.valueOf(dif.toUpperCase());
            List<Pregunta> preguntasFiltradas = filtrarPorDificultad(cursoSeleccionado.getPreguntas(), dificultad);

            if (preguntasFiltradas.isEmpty()) {
                throw new CursoSinPreguntasCiertaDificultad("No hay preguntas para la dificultad seleccionada.");
            }

            cursoEnProgresoActual = new CursoEnProgreso(cursoSeleccionado.getId(), estrategia_, preguntasFiltradas, dificultad);
            estadistica.registrarEstudioHoy();
            adaptadorCursoEnProgreso.guardar(cursoEnProgresoActual);

            return cursoEnProgresoActual;
        } else {
            throw new ExcepcionCursoActualVacio("Seleccione un curso antes de comenzar");
        }
    }

    /**
     * Corrige la respuesta del usuario a la pregunta actual.
     * 
     * @param respuesta Respuesta introducida por el usuario
     * @return true si es correcta, false si no
     */
    public boolean corregir(String respuesta) {
        Pregunta actual = cursoEnProgresoActual.getPreguntaActual();
        boolean acierto = actual.validarRespuesta(respuesta);
        estadistica.registrarRespuesta(acierto);
        return acierto;
    }

    /**
     * Avanza al siguiente paso en el curso actual y actualiza persistencia.
     */
    public void avanzarProgreso() {
        cursoEnProgresoActual.avanzar();
        adaptadorEstadistica.actualizar(estadistica);
        adaptadorCursoEnProgreso.actualizar(cursoEnProgresoActual);
    }

    /**
     * Reanuda un curso previamente guardado.
     * 
     * @param cursoSeleccionado Curso a reanudar
     * @return CursoEnProgreso reanudado o null si no existe
     */
    public CursoEnProgreso reanudarCurso(Curso cursoSeleccionado) {
        CursoEnProgreso cursoEnprogreso = adaptadorCursoEnProgreso.buscarPorCursoId(cursoSeleccionado.getId());
        if (cursoEnprogreso != null) {
            cursoEnprogreso.setPreguntas(filtrarPorDificultad(cursoSeleccionado.getPreguntas(), cursoEnprogreso.getDificultad()));
            cursoEnprogreso.reconstruirEstrategia();
            cursoEnProgresoActual = cursoEnprogreso;
        }
        return cursoEnprogreso;
    }

    /**
     * Devuelve la estadística actual.
     */
    public Estadistica getEstadistica() {
        return estadistica;
    }

    /**
     * Obtiene el número de preguntas respondidas.
     */
    public int getProgreso() {
        return cursoEnProgresoActual != null ? cursoEnProgresoActual.getProgreso() : 0;
    }

    /**
     * Obtiene el número total de preguntas del curso actual.
     */
    public int getTotalPreguntas() {
        return cursoEnProgresoActual != null ? cursoEnProgresoActual.getTotalPreguntas() : 0;
    }

    /**
     * Restablece las estadísticas de usuario.
     */
    public void restablecerEstadisticas() {
        estadistica.reset();
        adaptadorEstadistica.actualizar(estadistica);
    }

    /**
     * Devuelve el porcentaje de progreso actual en el curso.
     */
    public int getPorcentajeProgreso() {
        return (int) ((cursoEnProgresoActual.getProgreso() * 100.0f) / cursoEnProgresoActual.getTotalPreguntas());
    }

    /**
     * Finaliza la sesión actual del curso y actualiza estadísticas.
     * 
     * @return CursoEnProgreso (null después de finalizar)
     */
    public CursoEnProgreso finalizarSesionCurso() {
        if (cursoEnProgresoActual.isCompletado()) {
            adaptadorCursoEnProgreso.eliminar(cursoEnProgresoActual);
        } else {
            adaptadorCursoEnProgreso.actualizar(cursoEnProgresoActual);
        }
        estadistica.finalizarSesion();
        estadistica.setInicioSesion(LocalDateTime.now());
        adaptadorEstadistica.actualizar(estadistica);
        cursoEnProgresoActual = null;
        return cursoEnProgresoActual;
    }

    /**
     * Restablece el curso actual al inicio.
     */
    public CursoEnProgreso restablecerCurso() {
        cursoEnProgresoActual.setProgreso(CursoEnProgreso.PROGRESO_INICIAL);
        adaptadorCursoEnProgreso.actualizar(cursoEnProgresoActual);
        return cursoEnProgresoActual;
    }

    /**
     * Importa un curso desde un archivo.
     * 
     * @param archivo Archivo JSON o YAML
     * @return Curso importado
     * @throws IOException
     * @throws ExcepcionCursoDuplicado
     */
    public Curso importarCurso(File archivo) throws IOException, ExcepcionCursoDuplicado {
        return repoCursos.importarCurso(archivo);
    }

    /**
     * Exporta las estadísticas a un archivo.
     */
    public void exportarEstadisticas(String rutaArchivo) throws IOException {
        estadistica.exportar(rutaArchivo);
    }

    /**
     * Elimina un curso tanto de la persistencia como del repositorio en memoria.
     */
    public void eliminarCurso(Curso curso) {
        adaptadorCurso.eliminar(curso);
        adaptadorCursoEnProgreso.eliminarPorCursoId(curso.getId());
        repoCursos.eliminarCurso(curso.getId());
    }

    /**
     * Devuelve todos los cursos disponibles en el repositorio.
     */
    public List<Curso> getCursos() {
        return repoCursos.getCursos();
    }

    /**
     * Filtra preguntas por dificultad.
     */
    private List<Pregunta> filtrarPorDificultad(List<Pregunta> preguntas, Dificultad dificultad) {
        return preguntas.stream()
                .filter((p) -> p.getDificultad() == dificultad)
                .collect(Collectors.toList());
    }
}
