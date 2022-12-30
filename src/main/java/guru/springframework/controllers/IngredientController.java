package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.CommandToIngredientConverter;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.IngredientRepository;
import guru.springframework.domain.repositories.UnitOfMeasureRepository;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Controller
@Log4j2
public class IngredientController {

    private final IngredientService ingredientService;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CommandToIngredientConverter commandToIngredientConverter;
    private final RecipeService recipeService;

    public IngredientController(IngredientService ingredientService,
                                UnitOfMeasureRepository unitOfMeasureRepository,
                                CommandToIngredientConverter commandToIngredientConverter,
                                RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.commandToIngredientConverter = commandToIngredientConverter;
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{id}")
    public String showIngredient(@PathVariable Long id, Model model) {
        IngredientCommand ingredient = ingredientService.getIngredientCommandFromId(id);
        model.addAttribute("ingredient", ingredient);
        return "recipe/ingredients/show";
    }

    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String showIngredientForm(@PathVariable Long id, @PathVariable Long recipeId, Model model) {
        IngredientCommand ingredient = ingredientService.getIngredientCommandFromId(id);
        Set<UnitOfMeasureCommand> uomList = ingredientService.getAllUomCommands();
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("uomList", uomList);
        return "recipe/ingredients/ingredientform";
    }

    @Transactional
    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveIngredient(
            @PathVariable Long recipeId,
            @ModelAttribute IngredientCommand ingredientCommand,
            Model model
    ) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(i -> i.getId() == ingredientCommand.getId())
                .findFirst();
        if (!ingredientOptional.isPresent()) {
            recipe.getIngredients().add(commandToIngredientConverter.convert(ingredientCommand));
            recipeService.saveRecipe(recipe);
        }
        Ingredient ingredient = commandToIngredientConverter.convert(ingredientCommand);
        ingredientService.save(ingredient);
        return "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientCommand.getId();
    }

}
