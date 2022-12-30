package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UomToCommandConverterTest {

    private UnitOfMeasure uom;
    private UomToCommandConverter converter;
    private final static String descr = "TEST_DESCRIPTION";

    @BeforeEach
    public void beforeEach() {
        converter = new UomToCommandConverter();
    }

    @Test
    public void convertNull() {
        UnitOfMeasureCommand command = converter.convert(null);
        assertNull(command);
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        uom = new UnitOfMeasure();
        uom.setId(1L);
        uom.setUom(descr);
        UnitOfMeasureCommand command = converter.convert(uom);
        assertEquals(command.getUom(), descr);
        assertEquals(command.getId().longValue(), 1L);
    }
}