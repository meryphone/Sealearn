package utilsTest;

import static org.junit.jupiter.api.Assertions.*;
import utils.EstrategiaFactory;
import dominio.Estrategia;
import dominio.RepeticionEspaciada;

import org.junit.jupiter.api.Test;

class EstrategiaFactoryTest {

	@Test
	void construyeEstrategia() {
		Estrategia estrategia1 = new RepeticionEspaciada(10);
		Estrategia estrategia2 = EstrategiaFactory.crearEstrategia("RepeticionEspaciada", 10);
		assertTrue(estrategia1.getClass().equals(estrategia2.getClass()) &&
				 estrategia1.getTotalPreguntas() == estrategia2.getTotalPreguntas());
	}

}
