package guru.springframework.services;

import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageServiceImpl imageService;

    @BeforeEach
    public void beforeEach() {
        imageService = new ImageServiceImpl(recipeService);
    }

    byte[] byteObjArrayToPrimativeArray(Byte[] byteObj) {
        byte[] bytes = new byte[byteObj.length];
        int i = 0;
        for (Byte b: byteObj) {
            bytes[i++] = b.byteValue();
        }
        return bytes;
    }

    @Test
    public void saveImageFile() throws IOException  {

        byte[] imageBytes = "test image file".getBytes();
        MultipartFile file = new MockMultipartFile("imagefile", imageBytes);
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeService.getRecipeById(1L)).thenReturn(recipe);
        ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);
        imageService.saveImageFile(1L, file);
        verify(recipeService).saveRecipe(captor.capture());
        Recipe saved = captor.getValue();
        assertTrue(Arrays.equals(imageBytes,byteObjArrayToPrimativeArray(saved.getImage())));
    }
}
