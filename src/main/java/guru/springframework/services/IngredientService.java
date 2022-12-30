package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;

import java.io.IOException;
import java.util.Set;

public interface IngredientService {

    IngredientCommand getIngredientCommandFromId(Long id);
    Set<UnitOfMeasureCommand> getAllUomCommands();

    Ingredient save(Ingredient ingredient);
}
