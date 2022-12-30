package guru.springframework.commands;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class IngredientCommand {
    private Long id;
    private UnitOfMeasureCommand uom;
    private String description;
    private BigDecimal quantity;
}
