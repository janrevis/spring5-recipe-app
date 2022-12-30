package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToCommandConverter implements Converter<Ingredient, IngredientCommand> {

    private final UomToCommandConverter uomConverter;

    public IngredientToCommandConverter(UomToCommandConverter uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    @Nullable
    @Synchronized
    public IngredientCommand convert(Ingredient ingredient) {

        if (ingredient == null) {
            return null;
        }
        IngredientCommand command = new IngredientCommand();
        command.setId(ingredient.getId());
        command.setDescription(ingredient.getDescription());
        command.setUom(uomConverter.convert(ingredient.getUom()));
        command.setQuantity(ingredient.getQuantity());
        return command;
    }
}
