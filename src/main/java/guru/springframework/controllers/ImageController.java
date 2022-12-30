package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Log4j2
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/image")
    public String getImageUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(id));
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{id}/image")
    public String uploadImageFile(@PathVariable Long id, @RequestParam MultipartFile imagefile) {
        try {
            imageService.saveImageFile(id, imagefile);
        } catch (IOException e) {
            log.error("recipe image could not be saved");
            return "unexpectedError";
        }
        return "redirect:/recipe/" + id + "/show";
    }

    @RequestMapping("/recipe/{id}/image/render")
    public void renderRecipeImage(@PathVariable Long id,  HttpServletResponse response) throws IOException {
        Recipe recipe = recipeService.getRecipeById(id);
        ServletOutputStream out = response.getOutputStream();
        byte[] bytes = new byte[recipe.getImage().length];
        int i = 0;
        for (byte b: recipe.getImage()) {
            bytes[i++] = b;
        }
        out.write(bytes);
    }
}
