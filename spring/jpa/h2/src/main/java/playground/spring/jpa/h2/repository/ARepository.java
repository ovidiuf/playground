package playground.spring.jpa.h2.repository;

import org.springframework.data.repository.CrudRepository;
import playground.spring.jpa.h2.model.A;

public interface ARepository extends CrudRepository<A, Long> {
}
