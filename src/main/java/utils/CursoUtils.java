package utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import controlador.Controlador;
import dominio.Curso;
import dominio.Pregunta;
import dominio.PreguntaRellenarHueco;
import dominio.PreguntaRespuestaCorta;
import dominio.PreguntaTest;

public class CursoUtils {

	/**
	 * Carga un curso desde un archivo YAML ubicado en /resources/cursos/.
	 * 
	 * @param nombreArchivo nombre del archivo YAML
	 * @return el objeto Curso cargado o null si falla
	 */
	public static Curso importarCurso(String nombreArchivo) {
		String ruta = "/cursos/" + nombreArchivo;

		try (InputStream inputStream = Controlador.class.getResourceAsStream(ruta)) {
			if (inputStream == null) {
				System.err.println("Archivo no encontrado: " + ruta);
				return null;
			}

			// Crear constructor principal para Curso
			Constructor constructor = new Constructor(Curso.class);

			// Declarar que la propiedad "preguntas" contiene elementos polimórficos
			TypeDescription cursoDescription = new TypeDescription(Curso.class);
			cursoDescription.addPropertyParameters("preguntas", Pregunta.class);
			constructor.addTypeDescription(cursoDescription);

			// Añadir tipos concretos para deserialización polimórfica
			constructor.addTypeDescription(new TypeDescription(PreguntaTest.class, TagName("PreguntaTest")));
			constructor.addTypeDescription(
					new TypeDescription(PreguntaRellenarHueco.class, TagName("PreguntaRellenarHueco")));
			constructor.addTypeDescription(
					new TypeDescription(PreguntaRespuestaCorta.class, TagName("PreguntaRespuestaCorta")));

			// Crear YAML con el constructor configurado
			Yaml yaml = new Yaml(constructor);
			return yaml.load(inputStream);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Carga todos los cursos desde la carpeta /resources/cursos/
	 * 
	 * @return lista de cursos cargados correctamente
	 */
	public static List<Curso> cargarTodosLosCursos() {
		List<Curso> cursos = new ArrayList<>();

		try {
			URL folderURL = Controlador.class.getResource("/cursos/");
			if (folderURL == null) {
				System.err.println("Carpeta /cursos/ no encontrada.");
				return cursos;
			}

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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cursos;
	}

	/**
	 * Utilidad para crear tags simples para las clases hijas
	 */
	private static String TagName(String name) {
		return "!" + name;
	}
}
