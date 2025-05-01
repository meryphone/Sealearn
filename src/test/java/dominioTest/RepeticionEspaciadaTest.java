package dominioTest;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dominio.RepeticionEspaciada;

class RepeticionEspaciadaTest {

    @Test
    void testOrdenGeneradoCorrectamente() {
        RepeticionEspaciada estrategia = new RepeticionEspaciada(6);
        
        int[] esperado = {0, 1, 2, 0, 3, 4};
        
        for (int i = 0; i < esperado.length; i++) {
            int obtenido = estrategia.mostrarPregunta(i);
            assertEquals(esperado[i], obtenido);
        }
    }

    @Test
    void testMostrarPreguntaFueraDeRango() {
        RepeticionEspaciada estrategia = new RepeticionEspaciada(3); // genera 4 preguntas (0,1,2,0)
        assertEquals(-1, estrategia.mostrarPregunta(10)); // fuera de rango
    }
}
