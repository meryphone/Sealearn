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
	 * Carga un curso desde un archivo YAML dentro del classpath
	 *
	 * @param nombreArchivo
	 * @return Objeto Curso o null si hay error
	 */
	public static Curso importarCurso(String nombreArchivo) {
		String ruta = "/cursos/" + nombreArchivo;

		try (InputStream inputStream = Controlador.class.getResourceAsStream(ruta)) {
			if (inputStream == null) {
				System.exit(1); // PREGUNTAR avisar al usuario?
				return null;
			}

			Constructor constructor = new Constructor(Curso.class);

			TypeDescription cursoDesc = new TypeDescription(Curso.class);
			cursoDesc.addPropertyParameters("preguntas", Pregunta.class);
			constructor.addTypeDescription(cursoDesc);

			constructor.addTypeDescription(new TypeDescription(PreguntaTest.class, "!dominio.PreguntaTest"));
			constructor.addTypeDescription(new TypeDescription(PreguntaRellenarHueco.class, "!dominio.PreguntaHueco"));
			constructor.addTypeDescription(
					new TypeDescription(PreguntaRespuestaCorta.class, "!dominio.PreguntaRespuestaCorta"));

			Yaml yaml = new Yaml(constructor);
			Curso curso = yaml.load(inputStream);

			return curso;

		} catch (Exception e) {			
			e.printStackTrace(); // quitar
			System.exit(1);
			return null;
		}
	}

	/**
	 * Carga todos los cursos que haya en la carpeta "resources/cursos/".
	 * 
	 * Busca todos los archivos .yaml dentro de esa carpeta y los convierte en objetos Curso
	 * usando el método importarCurso(String).
	 * 
	 * @return una lista con todos los cursos que se han podido cargar correctamente.
	 */
	public static List<Curso> cargarTodosLosCursos() {
	    List<Curso> cursos = new ArrayList<>();

	    try {
	        URL folderURL = Controlador.class.getResource("/cursos/");
	        if (folderURL == null) {
	        	System.exit(1);
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
	        e.printStackTrace(); // QUITAR
	        System.exit(1);
	    }

	    return cursos;
	}


}
