package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {

        log.info("this is the recipe service");
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
