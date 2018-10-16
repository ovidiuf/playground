package playground.spring.sia.chapterthreejpa.tacocloud.persistence;

import org.springframework.data.repository.CrudRepository;
import playground.spring.sia.chapterthreejpa.tacocloud.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
