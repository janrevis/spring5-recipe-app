package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommandToIngredientConverter implements Converter<IngredientCommand, Ingredient> {

    private final CommandToUomConverter uomConverter;

    public CommandToIngredientConverter(CommandToUomConverter uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand command) {
        if (command == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(command.getId());
        ingredient.setQuantity(command.getQuantity());
        ingredient.setDescription(command.getDescription());
        ingredient.setUom(uomConverter.convert(command.getUom()));
        return ingredient;
    }
}
