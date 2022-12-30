package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashSet;
import java.util.Set;

public class IndexControllerTest {

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    IndexController indexController;
    Set<Recipe> receiptData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
        receiptData = new HashSet<>();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        receiptData.add(recipe);
    }

    @Test
    public void testMVC() throws Exception {
        MockMvc mockMVC = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMVC.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    public void indexController() {
        when(recipeService.getAllRecipes()).thenReturn(receiptData);
        String ret = indexController.getIndex(model);
        assertEquals(ret, "index");
        ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);
        verify(model, times(1)).addAttribute(eq("recipes"), captor.capture());
        verify(recipeService, times(1)).getAllRecipes();
        assertEquals(captor.getValue().size(), 1);
        System.out.println(captor.getAllValues());
    }
}
