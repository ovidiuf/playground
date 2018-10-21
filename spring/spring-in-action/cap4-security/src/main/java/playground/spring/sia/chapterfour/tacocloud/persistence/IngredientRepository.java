package playground.spring.sia.chapterfour.tacocloud.persistence;

import org.springframework.data.repository.CrudRepository;
import playground.spring.sia.chapterfour.tacocloud.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
