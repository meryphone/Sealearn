package controladorTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import persistencia.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import dominio.Secuencial;
import dominio.Estrategia;
import dominio.Pregunta;
import dominio.CursoEnProgreso;

class ControladorTest {
	
	private AdaptadorCursoEnProgresoJPA adaptadorCursoEnProgreso;
	private AdaptadorEstadisticaJPA adaptadorEstadistica;	
	private CursoEnProgreso cursoEnProgreso;
	
	/**
	private static Arguments parametrosCurso(){
		Estrategia estrategia = new Secuencial(6);
		List<Pregunta> preguntas = new ArrayList()<Pregunta>;
		Pregunta pregunta1 = new PreguntaHueco(); // Hacer con chatgpt		
		return Argum;
	}
	*/
	
	@BeforeEach
	@ParameterizedTest
	@MethodSource("fechasParaEsVencida")
	void preparar() {
		adaptadorCursoEnProgreso = mock(AdaptadorCursoEnProgresoJPA.class);
		adaptadorEstadistica = mock(AdaptadorEstadisticaJPA.class);
		cursoEnProgreso = new CursoEnProgreso();
	}

}
