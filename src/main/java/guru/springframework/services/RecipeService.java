package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);
    RecipeCommand getRecipeCommandById(Long id);

    Recipe saveRecipe(Recipe recipe);

    void deleteById(Long id);
}
