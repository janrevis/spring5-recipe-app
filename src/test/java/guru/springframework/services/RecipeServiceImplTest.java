package guru.springframework.services;

import guru.springframework.converters.RecipeToCommandConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToCommandConverter recipeToCommandConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeToCommandConverter);
    }

    @Test
    public void getAllRecipes() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Set<Recipe> mockRecipes = new HashSet<>();
        mockRecipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(mockRecipes);
        Set<Recipe> recipes = recipeService.getAllRecipes();
        assertEquals(recipes.size(), 1);
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(any()))
                .thenReturn(Optional.of(recipe));
        Recipe foundRecipe = recipeService.getRecipeById(1L);
        assertEquals(recipe, foundRecipe);
        verify(recipeRepository).findById(1L);
    }

    @Test
    public void deleteById() {
        recipeService.deleteById(1L);
        verify(recipeRepository, times(1)).deleteById(1L);
    }
}
