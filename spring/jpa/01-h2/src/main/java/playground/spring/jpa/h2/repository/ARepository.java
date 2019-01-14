package playground.spring.jpa.h2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import playground.spring.jpa.h2.model.A;

@Repository
public interface ARepository extends CrudRepository<A, Long> {

    A findByName(String name);
}
