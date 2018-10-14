package playground.spring.sia.chapterthree.tacocloud.persistence;

import playground.spring.sia.chapterthree.tacocloud.model.Taco;

public interface TacoRepository {

    Taco save(Taco design);
}
