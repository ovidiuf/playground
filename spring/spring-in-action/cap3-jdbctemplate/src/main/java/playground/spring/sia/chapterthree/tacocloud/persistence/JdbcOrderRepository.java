package playground.spring.sia.chapterthree.tacocloud.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import playground.spring.sia.chapterthree.tacocloud.model.Order;
import playground.spring.sia.chapterthree.tacocloud.model.Taco;

import java.util.*;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {

        this.orderInserter =
                new SimpleJdbcInsert(jdbcTemplate).
                        withTableName("TACO_ORDER").
                        usingGeneratedKeyColumns("id");

        this.orderInserter =
                new SimpleJdbcInsert(jdbcTemplate).
                        withTableName("TACO_ORDER_TACOS");

        this.objectMapper = new ObjectMapper();
    }

    //
    // Reading data
    //

    //
    // Writing data
    //

    @Override
    public Order save(Order order) {

        order.setPlacedAt(new Date());

        long orderId = saveOrderDetails(order);
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();

        for(Taco taco: tacos) {

            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    private long saveOrderDetails(Order order) {

        //noinspection unchecked
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);

        values.put("placedAt", order.getPlacedAt());

        return orderInserter.executeAndReturnKey(values).longValue();
    }

    private void saveTacoToOrder(Taco taco, long orderId) {

        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
