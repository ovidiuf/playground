package playground.spring.jpa.attributeConverter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import playground.spring.jpa.attributeConverter.model.A;

@Repository
public interface ARepository extends CrudRepository<A, Long> {
}
