package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeToCommandConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    private RecipeToCommandConverter recipeToCommandConverter;

    public RecipeServiceImpl(
            RecipeRepository recipeRepository,
            RecipeToCommandConverter recipeToCommandConverter
    ) {

        log.info("this is the recipe service");
        this.recipeRepository = recipeRepository;
        this.recipeToCommandConverter = recipeToCommandConverter;
    }

    public Set<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public RecipeCommand getRecipeCommandById(Long id) {
        return recipeToCommandConverter.convert(this.getRecipeById(id));
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
