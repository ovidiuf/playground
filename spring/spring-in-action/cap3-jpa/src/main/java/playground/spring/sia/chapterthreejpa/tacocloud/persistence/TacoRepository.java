package playground.spring.sia.chapterthreejpa.tacocloud.persistence;

import org.springframework.data.repository.CrudRepository;
import playground.spring.sia.chapterthreejpa.tacocloud.model.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
