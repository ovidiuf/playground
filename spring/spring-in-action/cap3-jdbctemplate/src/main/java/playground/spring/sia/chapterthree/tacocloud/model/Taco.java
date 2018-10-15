package playground.spring.sia.chapterthree.tacocloud.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import playground.spring.sia.chapterthree.tacocloud.persistence.IngredientRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least one ingredient")
    private List<Ingredient> ingredients;

    private IngredientRepository ingredientRepository;

    /**
     * TODO this is a hack as the form data maps onto ingredient IDs and I need Ingredient instances. I am sure
     * there is a better solution.
     *
     * @param ingredientIds
     */

    public void setIngredients(String[] ingredientIds) {

        this.ingredients = new ArrayList<>();

        for (String iid : ingredientIds) {

            if ("FLTO".equals(iid)) {
                ingredients.add(new Ingredient("FLTO", "Flour Tortillas", Ingredient.Type.WRAP));
            } else if ("COTO".equals(iid)) {
                ingredients.add(new Ingredient("COTO", "Corn Tortillas", Ingredient.Type.WRAP));
            } else if ("GRBF".equals(iid)) {
                ingredients.add(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            } else if ("CARN".equals(iid)) {
                ingredients.add(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            } else if ("TMTO".equals(iid)) {
                ingredients.add(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            } else if ("LETC".equals(iid)) {
                ingredients.add(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            } else if ("CHED".equals(iid)) {
                ingredients.add(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            } else if ("JACK".equals(iid)) {
                ingredients.add(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            } else if ("SLSA".equals(iid)) {
                ingredients.add(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            } else if ("SRCR".equals(iid)) {
                ingredients.add(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
            }
        }
    }
}
