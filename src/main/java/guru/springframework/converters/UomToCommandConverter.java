package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UomToCommandConverter implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Override
    @Synchronized
    public UnitOfMeasureCommand convert(UnitOfMeasure uom) {
        if (uom == null) {
            return null;
        }
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setUom(uom.getUom());
        command.setId(uom.getId());
        return command;
    }
}
