package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCommandConveter implements Converter<Category, CategoryCommand> {

    @Override
    @Synchronized
    @Nullable
    public CategoryCommand convert(Category category) {
        if (category == null) {
            return null;
        }
        CategoryCommand command = new CategoryCommand();
        command.setId(category.getId());
        command.setDescription(category.getDescription());
        return command;
    }
}
