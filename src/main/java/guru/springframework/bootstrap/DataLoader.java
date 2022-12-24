package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.repositories.CategoryRepository;
import guru.springframework.domain.repositories.RecipeRepository;
import guru.springframework.domain.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;

    public DataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Recipe guac = new Recipe();

        Ingredient avacados = new Ingredient();
        avacados.setDescription("avacado");
        avacados.setQuantity(new BigDecimal("1"));
        avacados.setUom(unitOfMeasureRepository.findByUom("count").orElse(null));

        Ingredient limeJuice = new Ingredient();
        limeJuice.setDescription("fresh lime or lemon juice");
        limeJuice.setQuantity(new BigDecimal("1"));
        limeJuice.setUom(unitOfMeasureRepository.findByUom("tablespoon").orElse(null));

        Ingredient onion = new Ingredient();
        onion.setDescription("minced red onion or thinly sliced green onion");
        onion.setQuantity(new BigDecimal("4"));
        onion.setUom(unitOfMeasureRepository.findByUom("tablespoon").orElse(null));

        Ingredient chili = new Ingredient();
        chili.setDescription("serrano (or jalapeño) chilis, stems and seeds removed, minced");
        chili.setQuantity(new BigDecimal("2"));
        chili.setUom(unitOfMeasureRepository.findByUom("count").orElse(null));

        Ingredient cilantro = new Ingredient();
        cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
        cilantro.setQuantity(new BigDecimal("2"));
        cilantro.setUom(unitOfMeasureRepository.findByUom("tablespoon").orElse(null));

        Ingredient pepper = new Ingredient();
        pepper.setDescription("freshly ground black pepper");
        pepper.setQuantity(new BigDecimal("1"));
        pepper.setUom(unitOfMeasureRepository.findByUom("pinch").orElse(null));

        Ingredient tomato = new Ingredient();
        tomato.setDescription("ripe tomato, chopped (optional)");
        tomato.setQuantity(new BigDecimal(".5"));
        tomato.setUom(unitOfMeasureRepository.findByUom("count").orElse(null));

        Ingredient radish = new Ingredient();
        radish.setDescription("Red radish or jicama slices for garnish (optional)");

        Ingredient chips= new Ingredient();
        chips.setDescription("Tortilla chips");

        guac.setDescription("");
        guac.setDifficulty(Difficulty.EASY);
        guac.setDirections(
            "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon.\n" +
            "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.\n" +
            "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
            "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
            "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
            "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
            "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
            "Refrigerate leftover guacamole up to 3 days.\n" +
            "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving."
        );
        guac.setDescription("Simple Guacamoli");
        guac.addIngredient(cilantro);
        guac.addIngredient(tomato);
        guac.addIngredient(pepper);
        guac.addIngredient(chili);
        guac.addIngredient(chips);
        guac.addIngredient(limeJuice);
        guac.addIngredient(avacados);
        guac.addIngredient(radish);
        guac.addIngredient(onion);

        guac.getCategories().add(categoryRepository.findByDescription("Mexican").orElse(null));
        recipeRepository.save(guac);
    }
}
