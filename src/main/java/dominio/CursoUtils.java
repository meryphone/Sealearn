package dominio;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CursoUtils {

	
    // Método para importar un curso desde una ruta absoluta
    public static Curso importarCursoDesdeYaml(String rutaArchivo) throws Exception {	// Manejar excepción en la interfaz
        Yaml yaml = new Yaml();
        
        InputStream inputStream = new FileInputStream(rutaArchivo);
            // Cargar el YAML como un Map
            Map<String, Object> yamlMap = yaml.load(inputStream);

            // Convertir el Map en un objeto Curso
            Curso curso = mapToCurso(yamlMap);
            System.out.println("Curso importado correctamente desde: " + rutaArchivo);
            return curso;        
    }

    // Métodos mapToCurso y mapToPregunta 
    @SuppressWarnings("unchecked")
	private static Curso mapToCurso(Map<String, Object> map) {
        Curso curso = new Curso();
        curso.setNombre((String) map.get("nombre"));
        curso.setDescripcion((String) map.get("descripcion"));
        curso.setEstrategia((String) map.get("estrategia"));
        curso.setDificultad((String) map.get("dificultad"));
        curso.setProgreso((int) map.get("progreso"));

        // Convertir la lista de preguntas
        List<Map<String, Object>> preguntasMap = (List<Map<String, Object>>) map.get("preguntas");
        List<Pregunta> preguntas = new ArrayList<>();
        for (Map<String, Object> preguntaMap : preguntasMap) {
            Pregunta pregunta = mapToPregunta(preguntaMap);
            preguntas.add(pregunta);
        }
        curso.setPreguntas(preguntas);

        return curso;
    }

    @SuppressWarnings("unchecked")
	private static Pregunta mapToPregunta(Map<String, Object> map) {
        String tipo = (String) map.get("tipo"); // Obtener el tipo de pregunta
        String enunciado = (String) map.get("enunciado");
        String respuestaCorrecta = (String) map.get("respuesta_correcta");
        List<String> opciones = new ArrayList<String>();
        
        switch (tipo) {
            case "PreguntaTest":
                opciones = (List<String>) map.get("opciones");
                return new PreguntaTest(enunciado, respuestaCorrecta, opciones);
            case "PreguntaVerdaderoFalso":
            	opciones = (List<String>) map.get("opciones");
                return new PreguntaHueco(enunciado, respuestaCorrecta, opciones);
            case "PreguntaAbierta":
                return new PreguntaRespuestaCorta(enunciado, respuestaCorrecta);
            default:
                throw new IllegalArgumentException("Tipo de pregunta no válido: " + tipo);
        }
    }
}