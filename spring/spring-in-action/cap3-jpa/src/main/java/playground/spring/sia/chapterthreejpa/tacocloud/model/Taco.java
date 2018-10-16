package playground.spring.sia.chapterthreejpa.tacocloud.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    private Date createdAt;

    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "You must choose at least one ingredient")
    private List<Ingredient> ingredients;

    @PrePersist
    void setCreatedAt() {

        this.createdAt = new Date();
    }

//    /**
//     * TODO this is a hack as the form data maps onto ingredient IDs and I need Ingredient instances. I am sure
//     * there is a better solution.
//     *
//     * @param ingredientIds
//     */
//    public void setIngredients(String[] ingredientIds) {
//
//        this.ingredients = new ArrayList<>();
//
//        for (String iid : ingredientIds) {
//
//            if ("FLTO".equals(iid)) {
//                ingredients.add(new Ingredient("FLTO", "Flour Tortillas", Ingredient.Type.WRAP));
//            } else if ("COTO".equals(iid)) {
//                ingredients.add(new Ingredient("COTO", "Corn Tortillas", Ingredient.Type.WRAP));
//            } else if ("GRBF".equals(iid)) {
//                ingredients.add(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
//            } else if ("CARN".equals(iid)) {
//                ingredients.add(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
//            } else if ("TMTO".equals(iid)) {
//                ingredients.add(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
//            } else if ("LETC".equals(iid)) {
//                ingredients.add(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
//            } else if ("CHED".equals(iid)) {
//                ingredients.add(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
//            } else if ("JACK".equals(iid)) {
//                ingredients.add(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
//            } else if ("SLSA".equals(iid)) {
//                ingredients.add(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
//            } else if ("SRCR".equals(iid)) {
//                ingredients.add(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
//            }
//        }
//    }
}
