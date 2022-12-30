package guru.springframework.services;

import guru.springframework.domain.Recipe;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeService recipeService;

    public ImageServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public void saveImageFile(Long recipeId, MultipartFile file) throws IOException  {
        System.out.println("recieving the file");
        Recipe recipe = recipeService.getRecipeById(recipeId);
        byte[] bytes = file.getBytes();
        Byte[] byteObj = new Byte[bytes.length];
        int i = 0;
        for (byte b: bytes) {
            byteObj[i++] = b;
        }
        recipe.setImage(byteObj);
        recipeService.saveRecipe(recipe);
    }
}
