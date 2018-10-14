package playground.spring.sia.chapterthree.tacocloud.persistence;

import playground.spring.sia.chapterthree.tacocloud.model.Ingredient;

public interface IngredientRepository {

    Ingredient findOne(String id);

    Iterable<Ingredient> findAll();

    Ingredient save(Ingredient ingredient);
}
