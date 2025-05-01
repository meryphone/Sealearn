package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import controlador.Controlador;
import dominio.Curso;
import excepciones.ExcepcionCursoDuplicado;

public class CursoUtils {

	private static final Set<UUID> cursosCargados = new HashSet<>();
	private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

	/**
	 * Importa un solo curso desde YAML usando Jackson. Detecta duplicados por UUID.
	 * 
	 * @param nombreArchivo nombre del archivo YAML
	 * @return el objeto Curso
	 * @throws ExcepcionCursoDuplicado si el UUID ya ha sido cargado
	 * @throws IOException             si ocurre un error de lectura
	 */
	public static Curso importarCurso(String nombreArchivo) throws IOException, ExcepcionCursoDuplicado {
	    InputStream inputStream = Controlador.class.getResourceAsStream("/cursos/" + nombreArchivo);
	    if (inputStream == null) {
	        throw new IOException("Archivo no encontrado: /cursos/" + nombreArchivo);
	    }
	    return importarCursoDesdeStream(inputStream);
	}

	
	public static Curso importarCurso(File archivo) throws IOException, ExcepcionCursoDuplicado {
	    try (InputStream inputStream = new java.io.FileInputStream(archivo)) {
	        return importarCursoDesdeStream(inputStream);
	    }
	}

	private static Curso importarCursoDesdeStream(InputStream inputStream) throws IOException, ExcepcionCursoDuplicado {
	    Curso curso = yamlMapper.readValue(inputStream, Curso.class);

	    if (cursosCargados.contains(curso.getId())) {
	        throw new ExcepcionCursoDuplicado("Curso duplicado detectado con UUID: " + curso.getId());
	    }

	    cursosCargados.add(curso.getId());
	    return curso;
	}


	/**
	 * Carga todos los cursos desde la carpeta /resources/cursos/
	 */
	public static List<Curso> cargarTodosLosCursos() {
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