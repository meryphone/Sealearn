package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import controlador.Controlador;
import dominio.Curso;
import dominio.Pregunta;
import dominio.PreguntaRellenarHueco;
import dominio.PreguntaRespuestaCorta;
import dominio.PreguntaTest;
import excepciones.ExcepcionCursoDuplicado;

public class CursoUtils {

	private static final Set<UUID> cursosCargados = new HashSet<>();

	/**
	 * Importa un solo curso desde YAML. Detecta duplicados por UUID.
	 * 
	 * @param nombreArchivo nombre del archivo YAML
	 * @return el objeto Curso
	 * @throws ExcepcionCursoDuplicado si el UUID ya ha sido cargado
	 * @throws IOException 
	 */
	public static Curso importarCurso(String nombreArchivo) throws ExcepcionCursoDuplicado, IOException {
		String ruta = "/cursos/" + nombreArchivo;

		    InputStream inputStream = Controlador.class.getResourceAsStream(ruta) ;
			if (inputStream == null) {
				throw new IOException("Archivo no encontrado: " + ruta);
			}

			Constructor constructor = new Constructor(Curso.class);
			TypeDescription cursoDescription = new TypeDescription(Curso.class);
			cursoDescription.addPropertyParameters("preguntas", Pregunta.class);
			constructor.addTypeDescription(cursoDescription);

			constructor.addTypeDescription(new TypeDescription(PreguntaTest.class, TagName("PreguntaTest")));
			constructor.addTypeDescription(new TypeDescription(PreguntaRellenarHueco.class, TagName("PreguntaRellenarHueco")));
			constructor.addTypeDescription(new TypeDescription(PreguntaRespuestaCorta.class, TagName("PreguntaRespuestaCorta")));

			Yaml yaml = new Yaml(constructor);
			Curso curso = yaml.load(inputStream);

			if (cursosCargados.contains(curso.getId())) {
				throw new ExcepcionCursoDuplicado("Curso duplicado detectado con UUID: " + curso.getId());
			}

			cursosCargados.add(curso.getId());
			return curso;

		
	}

	/**
	 * Carga todos los cursos desde la carpeta /resources/cursos/
	 * Llama internamente a importarCurso() que ya detecta duplicados.
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
					Curso curso = importarCurso(archivo.getName());
					if (curso != null) {
						cursos.add(curso);
					}
				}
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException("Error al acceder a la carpeta /cursos/", e);
		}catch(ExcepcionCursoDuplicado e) {
			// No se importa el duplicado y sigue importando.
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return cursos;
	}

	private static String TagName(String name) {
		return "!" + name;
	}
}
