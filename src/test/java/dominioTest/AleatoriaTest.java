package dominioTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dominio.Aleatoria;

public class AleatoriaTest  {
	
	@Test
	void testAleatoria() {
		Aleatoria aleatoria = new Aleatoria(5);
		for(int i = 0; i<5; i++) {	
			int idx = aleatoria.mostrarPregunta(i);
			assertTrue(idx >= 0 && idx < 5);
		}
	}
	
	@Test
	void testMostrarPreguntasFueraDeRango() {
		Aleatoria aleatoria = new Aleatoria(3);
	    assertEquals(-1, aleatoria.mostrarPregunta(5)); // fuera de rango
	}
	
}
