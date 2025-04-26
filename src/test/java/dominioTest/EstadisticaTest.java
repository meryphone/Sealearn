package dominioTest;

import java.time.LocalDate;
import java.util.List;

import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import dominio.Estadistica;


public class EstadisticaTest extends TestCase {
	
	@ParameterizedTest
	@CsvSource({
        "true, 1, 0, 1",   
        "false, 0, 1, 1",  
        "false, 0, 1, 1"   
    })
	void testRegistrarRespuesta(boolean esCorrecta, int aciertosEsperados, int fallosEsperados, int totalEsperado) {
        Estadistica estadistica = new Estadistica();
        estadistica.registrarRespuesta(esCorrecta);
        assertEquals(aciertosEsperados, estadistica.getTotalAciertos());
        assertEquals(fallosEsperados, estadistica.getTotalFallos());
        assertEquals(totalEsperado, estadistica.getTotalPreguntasRespondidas());
    }
	
	private static List<Arguments> fechas() {
		return List.of(
			Arguments.of(LocalDate.of(2025, 01, 20), 1),
			Arguments.of(LocalDate.of(2025, 3, 31), 1),
			Arguments.of(null, 1)
		);
	}
	
	@ParameterizedTest
	@MethodSource("fechas")
	void testRegistrarEstudioHoy(LocalDate ultimoDia, int rachaActual) {
		Estadistica estadistica = new Estadistica();
		estadistica.setUltimoDiaEstudio(ultimoDia);
		estadistica.registrarEstudioHoy();
		assertEquals(rachaActual, estadistica.getRachaActual());
	}
	
	@Test
	void testFinalizarSesion() throws InterruptedException {
	    Estadistica estadistica = new Estadistica();
	    Thread.sleep(100); // simulamos que pasa algo de tiempo
	    estadistica.finalizarSesion();

	    assertNotNull(estadistica.getTiempoTotalEstudio());
	    assertTrue(estadistica.getTiempoTotalEstudio().toMillis() >= 100);
	    assertNull(estadistica.getInicioSesion()); // debe estar cerrada
	}

}
