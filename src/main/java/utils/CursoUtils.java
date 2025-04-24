package utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	/**
	 * Carga un curso desde un archivo YAML ubicado en /resources/cursos/.
	 * 
	 * @param nombreArchivo nombre del archivo YAML
	 * @return el objeto Curso cargado o null si falla
	 */
	public static Curso importarCurso(String nombreArchivo, List<UUID> idsYaImportados) throws ExcepcionCursoDuplicado {
		String ruta = "/cursos/" + nombreArchivo;

		try (InputStream inputStream = Controlador.class.getResourceAsStream(ruta)) {
			if (inputStream == null) {
				System.err.println("Archivo no encontrado: " + ruta);
				return null;
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

			if (idsYaImportados.contains(curso.getId())) {
				throw new ExcepcionCursoDuplicado("Curso duplicado detectado (UUID): " + curso.getId());
			}

			idsYaImportados.add(curso.getId());
			return curso;

		} catch (ExcepcionCursoDuplicado e) {
			throw e;
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
	public static List<Curso> cargarTodosLosCursos() throws ExcepcionCursoDuplicado {
		List<Curso> cursos = new ArrayList<>();
		List<UUID> idsImportados = new ArrayList<>();

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
					Curso curso = importarCurso(archivo.getName(), idsImportados);
					if (curso != null) {
						cursos.add(curso);
					}
				}
			}

		} catch (ExcepcionCursoDuplicado e) {
			throw e;
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
