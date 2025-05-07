package utilsTest;

import utils.DurationAttributeConverter;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DurationAttributeConverterTest {

    private final DurationAttributeConverter converter = new DurationAttributeConverter();

    @Test
    void testConvertToDatabaseColumn() {
        Duration duracion = Duration.ofSeconds(90);
        Long result = converter.convertToDatabaseColumn(duracion);
        assertEquals(90L, result);
    }

    @Test
    void testConvertToDatabaseColumnNull() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        Long valor = 120L;
        Duration duration = converter.convertToEntityAttribute(valor);
        assertEquals(Duration.ofSeconds(120), duration);
    }

    @Test
    void testConvertToEntityAttributeNull() {
        Duration result = converter.convertToEntityAttribute(null);
        assertEquals(Duration.ZERO, result);
    }
}
