package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void getImageUploadForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.getRecipeCommandById(any())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(MockMvcResultMatchers.model()
                        .attribute("recipe", recipeCommand));
    }

    @Test
    public void updateImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "imagefile",
                "testing.txt",
                "text/plain",
                "file content".getBytes());
        mockMvc.perform(multipart("/recipe/1/image").file(file))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));
        verify(imageService).saveImageFile(anyLong(), any());
    }

    @Test
    public void renderImage() throws Exception {
        Recipe recipe = new Recipe();
        byte[] testImage = "test image".getBytes();
        Byte[] recipeImage = new Byte[testImage.length];
        int i = 0;
        for (byte b: testImage) {
            recipeImage[i++] = b;
        }
        recipe.setImage(recipeImage);
        when(recipeService.getRecipeById(1L)).thenReturn(recipe);
        MockHttpServletResponse response = mockMvc
                .perform(get("/recipe/1/image/render"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] image = response.getContentAsByteArray();
        assertTrue(Arrays.equals(testImage, image));
    }
}
