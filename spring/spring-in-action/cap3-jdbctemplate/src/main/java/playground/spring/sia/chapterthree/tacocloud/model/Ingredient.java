package playground.spring.sia.chapterthree.tacocloud.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
public class Ingredient {

    private final String id;
    private final String name;

    // @ToString.Exclude
    private final Type type;

    public enum Type {

        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
