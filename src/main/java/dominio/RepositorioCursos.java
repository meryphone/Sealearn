package dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import controlador.Controlador;
import excepciones.ExcepcionCursoDuplicado;
import persistencia.AdaptadorCursoJPA;
import persistencia.ICurso;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Repositorio central de cursos disponibles en el sistema.
 * 
 * Implementa un patrón Singleton y se encarga de cargar cursos desde base de datos
 * o desde ficheros YAML ubicados en la carpeta de recursos.
 */
public class RepositorioCursos {

    private final Map<UUID, Curso> cursosCargados = new HashMap<>();

    private static RepositorioCursos repoCursos;

    private final ICurso adaptadorCurso;

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    private RepositorioCursos() {
        this.adaptadorCurso = AdaptadorCursoJPA.getInstance();
        this.inicializarRepositorio();
    }

    public static RepositorioCursos getInstance() {
        if (repoCursos == null) {
            repoCursos = new RepositorioCursos();
        }
        return repoCursos;
    }

    /**
     * Inicializa el repositorio cargando los cursos desde la base de datos.
     * Si la BD está vacía, se intentan importar cursos desde la carpeta /resources/cursos/.
     */
    private void inicializarRepositorio() {
        List<Curso> cursosBD = adaptadorCurso.buscarTodos();
        if (cursosBD.isEmpty()) {
            List<Curso> cursosImportados = cargarCursosDesdeCarpetaRecursos();
            for (Curso curso : cursosImportados) {
                adaptadorCurso.guardar(curso);
                cursosCargados.put(curso.getId(), curso);
            }
        } else {
            for (Curso curso : cursosBD) {
                cursosCargados.put(curso.getId(), curso);
            }
        }
    }

    /**
     * Importa un curso desde un archivo YAML externo y lo guarda si no está duplicado.
     *
     * @param archivo archivo .yaml con los datos del curso
     * @return el Curso importado
     * @throws ExcepcionCursoDuplicado si ya existe un curso con el mismo UUID
     * @throws IOException si hay errores de lectura del archivo
     */
    public Curso importarCurso(File archivo) throws ExcepcionCursoDuplicado, IOException {
        Curso curso = yamlMapper.readValue(archivo, Curso.class);

        if (cursosCargados.containsKey(curso.getId())) {
            throw new ExcepcionCursoDuplicado("Curso duplicado detectado con UUID: " + curso.getId());
        }

        cursosCargados.put(curso.getId(), curso);
        adaptadorCurso.guardar(curso);
        return curso;
    }

    /**
     * Elimina un curso del repositorio y de la base de datos.
     *
     * @param cursoID identificador del curso a eliminar
     */
    public void eliminarCurso(UUID cursoID) {
        Curso curso = cursosCargados.remove(cursoID);
        if (curso != null) {
            adaptadorCurso.eliminar(curso);
        }
    }

    /**
     * Devuelve todos los cursos actualmente cargados en el sistema.
     *
     * @return lista inmutable de cursos
     */
    public List<Curso> getCursos() {
        return Collections.unmodifiableList(new ArrayList<>(cursosCargados.values()));
    }

    /**
     * Carga cursos desde la carpeta de recursos `/cursos/` en formato YAML.
     * Solo se ejecuta si la base de datos está inicialmente vacía.
     *
     * @return lista de cursos importados exitosamente
     */
    private List<Curso> cargarCursosDesdeCarpetaRecursos() {
        List<Curso> cursos = new ArrayList<>();
        URL folderURL = Controlador.class.getResource("/cursos/");
        if (folderURL == null) {
            throw new RuntimeException("Carpeta /cursos/ no encontrada");
        }

        try {
            File folder = new File(folderURL.toURI());
            File[] archivos = folder.listFiles((dir, name) -> name.endsWith(".yaml"));

            if (archivos != null) {
                for (File archivo : archivos) {
                    try {
                        Curso curso = yamlMapper.readValue(archivo, Curso.class);
                        if (!cursosCargados.containsKey(curso.getId())) {
                            cursos.add(curso);
                        }
                    } catch (IOException e) {
                        System.err.println("Error leyendo archivo: " + archivo.getName());
                    }
                }
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error al acceder a la carpeta /cursos/", e);
        }

        return cursos;
    }
}
