package playground.spring.sia.chapterfour.tacocloud.persistence;

import org.springframework.data.repository.CrudRepository;
import playground.spring.sia.chapterfour.tacocloud.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
