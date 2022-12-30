package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientToCommandConverterTest {

    private Ingredient ingredient;
    private IngredientToCommandConverter converter;
    private final static String descr = "TEST_DESCRIPTION";

    @BeforeEach
    public void beforeEach() {
        converter = new IngredientToCommandConverter(new UomToCommandConverter());
    }

    @Test
    public void convertNull() {
        IngredientCommand command = converter.convert(null);
        assertNull(command);
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setUom("test uom");
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setDescription(descr);
        ingredient.setUom(uom);
        IngredientCommand command = converter.convert(ingredient);
        assertEquals(command.getDescription(), descr);
        assertEquals(command.getId().longValue(), 1L);
        assertEquals(command.getUom().getUom(), "test uom");
    }

}
