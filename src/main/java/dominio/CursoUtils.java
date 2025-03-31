package dominio;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CursoUtils {

    public static Curso importarCursoDesdeYaml(String rutaArchivo) throws Exception {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(rutaArchivo)) {
            Map<String, Object> yamlMap = yaml.load(inputStream);
            Curso curso = mapToCurso(yamlMap);
            return curso;
        }
    }

    @SuppressWarnings("unchecked")
    private static Curso mapToCurso(Map<String, Object> map) {
        Curso curso = new Curso();
        curso.setNombre((String) map.get("nombre"));
        curso.setDescripcion((String) map.get("descripcion"));

        // El progreso se inicia en 0 por defecto
        curso.setProgreso(0);

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
        String tipo = (String) map.get("tipo");
        String enunciado = (String) map.get("enunciado");
        String respuestaCorrecta = (String) map.get("respuesta_correcta");
        String dificultad = (String) map.get("dificultad");

        switch (tipo) {
            case "PreguntaTest":
                List<String> opcionesTest = (List<String>) map.get("opciones");
                return new PreguntaTest(enunciado, respuestaCorrecta, opcionesTest, dificultad);

            case "PreguntaHueco":
                List<String> opcionesHueco = (List<String>) map.get("opciones");
                return new PreguntaHueco(enunciado, respuestaCorrecta, opcionesHueco, dificultad);

            case "PreguntaRespuestaCorta":
                return new PreguntaRespuestaCorta(enunciado, respuestaCorrecta, dificultad);

            default:
                throw new IllegalArgumentException("Tipo de pregunta no válido: " + tipo);
        }
    }
}
