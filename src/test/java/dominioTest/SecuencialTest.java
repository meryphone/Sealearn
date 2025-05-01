package dominioTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import dominio.Secuencial;

class SecuencialTest {

	 @Test
	    void testOrdenGeneradoCorrectamente() {
	        Secuencial estrategia = new Secuencial(5);
	        
	        int[] esperado = {0, 1, 2, 3, 4};
	        
	        for (int i = 0; i < esperado.length; i++) {
	            int obtenido = estrategia.mostrarPregunta(i);
	            assertEquals(esperado[i], obtenido);
	        }
	    }
	 
	 @Test 
	 void testSecuencialFueraDeOrden() {
		 Secuencial estrategia = new Secuencial(5);
		 assertEquals(-1, estrategia.mostrarPregunta(6));
	 }

}
