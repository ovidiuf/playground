package playground.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.spring.jpa.model.Item;
import playground.spring.jpa.repository.ItemJpaRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootApplication
public class SpringBootJpaRepositoryApplication implements CommandLineRunner {

    public static final DateFormat TIMESTAMP = new SimpleDateFormat("MM/dd/yy");

    @Autowired
    private ItemJpaRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaRepositoryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //
        // We save three items in repository. The items are built in 2016, 2017, 2018. Then we query
        // for all items built between 01/01/17 and 01/01/18 (which means midnight 12/31/17) .
        // We should get just one item. We use the Spring Repository DDL to express the query.
        //

        Item i = new Item(TIMESTAMP.parse("07/01/16"));
        Item i2 = new Item(TIMESTAMP.parse("07/01/17"));
        Item i3 = new Item(TIMESTAMP.parse("07/01/18"));

        repository.save(i);
        repository.save(i2);
        repository.save(i3);

        List<Item> timeBoundedItems =
                repository.getItemByCreatedIsBetween(TIMESTAMP.parse("01/01/17"), TIMESTAMP.parse("01/01/18"));

        List<Item> allItems = repository.findAll();

        System.out.println("all items: " + allItems);
        System.out.println("items created between 01/01/17 and 01/01/18: " + timeBoundedItems);

        //
        // we then query for an item built between 2015 dates, we should get an empty list
        //

        List<Item> timeBoundedItems2 =
                repository.getItemByCreatedIsBetween(TIMESTAMP.parse("01/01/15"), TIMESTAMP.parse("12/31/15"));

        System.out.println(timeBoundedItems2.size() + " items found");
    }
}

