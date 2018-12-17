package playground.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.spring.jpa.model.Item;

import java.util.Date;
import java.util.List;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {

    List<Item> getItemByCreatedIsBetween(Date from, Date to);
}


