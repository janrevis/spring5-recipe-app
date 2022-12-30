package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientToCommandConverter;
import guru.springframework.converters.UomToCommandConverter;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.domain.repositories.IngredientRepository;
import guru.springframework.domain.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private IngredientToCommandConverter ingredientToCommandConverter;
    private UomToCommandConverter uomToCommandConverter;

    public IngredientServiceImpl(
            IngredientRepository ingredientRepository,
            UnitOfMeasureRepository unitOfMeasureRepository,
            IngredientToCommandConverter ingredientToCommandConverter,
            UomToCommandConverter uomToCommandConverter
    ) {
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToCommandConverter = ingredientToCommandConverter;
        this.uomToCommandConverter = uomToCommandConverter;
    }

    public IngredientCommand getIngredientCommandFromId(Long id) {
        Ingredient ingre = ingredientRepository.findById(id).orElse(null);
        return ingredientToCommandConverter.convert(ingre);
    }

    public Set<UnitOfMeasureCommand> getAllUomCommands() {
        Set<UnitOfMeasureCommand> commands = new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(uom -> {
            commands.add(uomToCommandConverter.convert(uom));
        });
        return commands;
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

}
