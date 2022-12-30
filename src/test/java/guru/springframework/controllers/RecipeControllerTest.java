package guru.springframework.controllers;


import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.*;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @Mock
    CommandToCategoryConverter commandToCategoryConverter;

    @Mock
    CommandToIngredientConverter commandToIngredientConverter;

    @Mock
    IngredientToCommandConverter ingredientToCommandConverter;

    @Mock
    CategoryToCommandConveter categoryToCommandConveter;

    @Mock
    RecipeToCommandConverter recipeToCommandConverter;

    @Mock
    CommandToRecipeConverter commandToRecipeConverter;

    @InjectMocks
    RecipeController recipeController;

    MockMvc mockMvc;

    Recipe recipe;

    @BeforeEach
    public void beforeEach() {

        recipe = new Recipe();
        recipe.setId(1L);
        recipeController = new RecipeController(
                recipeService,
                recipeToCommandConverter,
                commandToRecipeConverter
        );
        mockMvc = MockMvcBuilders
                .standaloneSetup(recipeController)
                .build();
    }

    @Test
    public void showRecipe() throws Exception {
        when(recipeService.getRecipeById(any())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model()
                                .attribute("recipe", recipe));
        verify(recipeService).getRecipeById(1L);
    }

    @Test
    public void showNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void showUpdateReceiptForm() throws Exception {
        RecipeCommand recipe = new RecipeCommand();
        when(recipeService.getRecipeCommandById(any())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));
        verify(recipeService).getRecipeCommandById(1L);
    }

    @Test
    public void saveRecipe() throws Exception {
        Recipe savedReceipt = new Recipe();
        savedReceipt.setId(1L);
        when(recipeService.saveRecipe(any())).thenReturn(savedReceipt);
        mockMvc.perform(post("/recipe"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/1/show"))
            .andExpect(
                    MockMvcResultMatchers.model().attribute("recipe", savedReceipt)
            );
    }

    @Test
    public void deleteReceipt() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService).deleteById(1L);
    }

//    @Test
//    public void listIngredients() throws Exception {
//        RecipeCommand recipe = new RecipeCommand();
//        when(recipeService.getRecipeCommandById(1L)).thenReturn(recipe);
//        mockMvc.perform(get("/recipe/1/ingredients"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/recipe/ingredients/list"));
//        verify(recipeService, times(1)).getRecipeCommandById(1L);
//    }

//    @Test
//    public void viewIngredient() throws Exception {
//        IngredientCommand ingredient = new IngredientCommand();
//        Recipe recipe = new Recipe();
//        recipe.setId(1L);
//        when(recipeService.getRecipeById(any())).thenReturn(recipe);
//        mockMvc.perform(get("/recipe/1/ingredient/1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("/recipe/ingredients/show"));
//
//    }

}
