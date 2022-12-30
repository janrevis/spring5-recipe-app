package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeToCommandConverter implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCommandConveter categoryConverter;
    private final IngredientToCommandConverter ingredientConverter;

    public RecipeToCommandConverter(
            CategoryToCommandConveter categoryConverter,
            IngredientToCommandConverter ingredientConverter
    ) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Override
    @Synchronized
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        RecipeCommand command = new RecipeCommand();
        command.setId(recipe.getId());
        command.setDescription(recipe.getDescription());
        command.setDifficulty(recipe.getDifficulty());
        command.setDirections(recipe.getDirections());
        command.setServings(recipe.getServings());
        command.setUrl(recipe.getUrl());
        command.setCookTime(recipe.getCookTime());
        command.setPrepTime(recipe.getPrepTime());
        command.setNotes(recipe.getNotes());

        Set<CategoryCommand> cats = new HashSet<>();
        if (recipe.getCategories() != null) {
            recipe.getCategories().forEach(cat -> {
                cats.add(categoryConverter.convert(cat));
            });
        }
        command.setCategories(cats);
        Set<IngredientCommand> ingredients = new HashSet<>();
        if (recipe.getIngredients() != null) {
            recipe.getIngredients().forEach(ingredient -> {
                ingredients.add(ingredientConverter.convert(ingredient));
            });
        }
        command.setIngredients(ingredients);
        return command;
    }
}
