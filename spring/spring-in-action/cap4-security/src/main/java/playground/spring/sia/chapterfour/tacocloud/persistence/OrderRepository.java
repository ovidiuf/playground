package playground.spring.sia.chapterfour.tacocloud.persistence;

import org.springframework.data.repository.CrudRepository;
import playground.spring.sia.chapterfour.tacocloud.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
