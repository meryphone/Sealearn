package dominio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import controlador.Controlador;
import excepciones.ExcepcionCursoDuplicado;

public class RepositorioCursos {

	private Map<UUID, Curso> cursosCargados ;
	private static RepositorioCursos repoCursos;
	private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
	
	
	private RepositorioCursos() {
		cursosCargados = new HashMap<UUID, Curso>();
		this.cargarTodosLosCursos();
	}
	
	public static RepositorioCursos getInstance() {
		if(repoCursos == null) {
			repoCursos = new RepositorioCursos();
		}
		return repoCursos;
	}

	/**
	 * Importa un solo curso desde YAML usando Jackson. Detecta duplicados por UUID.
	 * 
	 * @param nombreArchivo nombre del archivo YAML
	 * @return el objeto Curso
	 * @throws ExcepcionCursoDuplicado si el UUID ya ha sido cargado
	 * @throws IOException             si ocurre un error de lectura
	 */
	public Curso importarCurso(String nombreArchivo) throws ExcepcionCursoDuplicado, IOException {
		String ruta = "/cursos/" + nombreArchivo;

		InputStream inputStream = Controlador.class.getResourceAsStream(ruta);
		if (inputStream == null) {
			throw new IOException("Archivo no encontrado: " + ruta);
		}

		Curso curso = yamlMapper.readValue(inputStream, Curso.class);

		if (cursosCargados.containsKey(curso.getId())) {
			throw new ExcepcionCursoDuplicado("Curso duplicado detectado con UUID: " + curso.getId());
		}

		cursosCargados.put(curso.getId(),curso);
		return curso;
	}
	
	public void eliminarCurso(UUID cursoID) {
		cursosCargados.remove(cursoID);
	}
	
	public List<Curso> getCursos(){
		return Collections.unmodifiableList((ArrayList<Curso>) cursosCargados.values());
	}

	/**
	 * Carga todos los cursos desde la carpeta /resources/cursos/
	 */
	private List<Curso> cargarTodosLosCursos() {
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
						Curso curso = importarCurso(archivo.getName());
						if (curso != null) {
							cursos.add(curso);
						}
					} catch (ExcepcionCursoDuplicado e) {
					    System.err.println("Curso duplicado ignorado: " + e.getMessage());//Quitar
					}
				}
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException("Error al acceder a la carpeta /cursos/", e);
		} catch (IOException e) {
			throw new RuntimeException("Error leyendo un curso", e);
		}

		return cursos;
	}
}