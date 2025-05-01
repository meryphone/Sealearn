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
		Estrategia estrategia2 = EstrategiaFactory.crearEstrategia("Repeticion Espaciada", 10);
		assertTrue(estrategia1.equals(estrategia2));
	}

}
