package dominioTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import junit.framework.TestCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import dominio.RepeticionEspaciada;


import dominio.Estadistica;


class EstadisticaTest {
    private Estadistica estadistica;

    @BeforeEach
    void setUp() {
        estadistica = new Estadistica();
    }

    @Test
    void testRegistrarRespuesta() {
        estadistica.registrarRespuesta(true);
        estadistica.registrarRespuesta(false);

        assertEquals(2, estadistica.getTotalPreguntasRespondidas());
        assertEquals(1, estadistica.getTotalAciertos());
        assertEquals(1, estadistica.getTotalFallos());
    }

    @Test
    void testRegistrarEstudioHoy() {
        estadistica.setUltimoDiaEstudio(LocalDate.now().minusDays(1));
        estadistica.registrarEstudioHoy();
        assertEquals(1, estadistica.getRachaActual());
    }

    @Test
    void testFinalizarSesion() {
        estadistica.setInicioSesion(LocalDateTime.now().minusMinutes(60));
        estadistica.finalizarSesion();
        long minutos = estadistica.getTiempoTotalEstudio().toMinutes();
        assertTrue(minutos >= 59 && minutos <= 61); 
    }

    @Test
    void testReset() {
        estadistica.registrarRespuesta(true);
        estadistica.reset();
        assertEquals(0, estadistica.getTotalPreguntasRespondidas());
        assertEquals(0, estadistica.getTotalAciertos());
        assertEquals(0, estadistica.getRachaActual());
    }
}
