package playground.spring.sia.chapterthree.tacocloud.persistence;

import playground.spring.sia.chapterthree.tacocloud.model.Order;

public interface OrderRepository {

    Order save(Order order);
}
