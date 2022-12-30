package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeToCommandConverterTest {

    private RecipeToCommandConverter converter;
    private final static String descr = "TEST_DESCRIPTION";
    private Recipe recipe;

    @BeforeEach
    public void beforeEach() {
        converter = new RecipeToCommandConverter(
                new CategoryToCommandConveter(),
                new IngredientToCommandConverter(new UomToCommandConverter())
        );
    }

    @Test
    public void convertNull() {
        RecipeCommand command = converter.convert(null);
        assertNull(command);
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setRecipeNotes("note note");
        notes.setRecipeNotes("blah bah");
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDescription(descr);
        recipe.setCookTime(2);
        recipe.setPrepTime(10);
        recipe.setDirections("blah blah");
        recipe.setUrl("www");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setNotes(notes);
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription("test ingredient");
        Category cat = new Category();
        cat.setDescription("test cat");
        recipe.getCategories().add(cat);
        recipe.getIngredients().add(ingredient);
        RecipeCommand command = converter.convert(recipe);
        assertEquals(command.getDescription(), descr);
        assertEquals(command.getId().longValue(), 1L);
        assertEquals(command.getCategories().size(), 1);
        assertEquals(command.getIngredients().size(), 1);
        assertEquals(command.getCookTime(), recipe.getCookTime());
        assertEquals(command.getPrepTime(), recipe.getPrepTime());
        assertEquals(command.getDifficulty(), recipe.getDifficulty());
        assertEquals(command.getDirections(), recipe.getDirections());
        assertEquals(command.getDifficulty(), recipe.getDifficulty());
        assertEquals(command.getServings(), recipe.getServings());
        assertEquals(command.getNotes().getRecipeNotes(), notes.getRecipeNotes());
    }

}
