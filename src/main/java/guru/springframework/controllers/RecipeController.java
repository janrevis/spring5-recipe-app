package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.CommandToRecipeConverter;
import guru.springframework.converters.RecipeToCommandConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    RecipeService recipeService;
    RecipeToCommandConverter recipeToCommandConverter;
    CommandToRecipeConverter commandToRecipeConverter;

    public RecipeController(
            RecipeService recipeService,
            RecipeToCommandConverter recipeToCommandConverter,
            CommandToRecipeConverter commandToRecipeConverter
    ) {
        this.recipeService = recipeService;
        this.recipeToCommandConverter = recipeToCommandConverter;
        this.commandToRecipeConverter = commandToRecipeConverter;
    }

    @RequestMapping("/{id}/show")
    public String showRecipe(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        recipe.getCategories();
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }

    @RequestMapping("/new")
    public String newRecipeForm(Model model) {
        RecipeCommand command = new RecipeCommand();
        model.addAttribute("recipe", command);
        return "recipe/recipeform";
    }

    @RequestMapping("/{id}/update")
    public String updateRecipeForm(@PathVariable Long id,  Model model) {
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(id);
        model.addAttribute("recipe", recipeCommand);
        return "recipe/recipeform";
    }

    @RequestMapping("")
    @PostMapping
    public String postRecipe(@ModelAttribute RecipeCommand recipeCommand, Model model) {
        Recipe recipe = commandToRecipeConverter.convert(recipeCommand);
        Recipe saved = recipeService.saveRecipe(recipe);
        model.addAttribute("recipe", saved);
        return "redirect:/recipe/" + saved.getId() + "/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/ingredients")
    public String listIngredients(@PathVariable Long id, Model model) {
        RecipeCommand recipe = recipeService.getRecipeCommandById(id);
        model.addAttribute("recipe", recipe);
        return "/recipe/ingredients/list";
    }

}
