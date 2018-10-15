package playground.spring.sia.chapterthree.tacocloud.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public enum Type {

        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
