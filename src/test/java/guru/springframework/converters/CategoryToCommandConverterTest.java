package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryToCommandConverterTest {

    private Category category;
    private CategoryToCommandConveter converter;
    private final static String descr = "TEST_DESCRIPTION";

    @BeforeEach
    public void beforeEach() {
        converter = new CategoryToCommandConveter();
    }

    @Test
    public void convertNull() {
        CategoryCommand command = converter.convert(null);
        assertNull(command);
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        category = new Category();
        category.setId(1L);
        category.setDescription(descr);
        CategoryCommand command = converter.convert(category);
        assertEquals(command.getDescription(), descr);
        assertEquals(command.getId().longValue(), 1L);
    }
}
