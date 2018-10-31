package playground.spring.jpa.attributeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.spring.jpa.attributeConverter.model.A;
import playground.spring.jpa.attributeConverter.repository.ARepository;

import java.util.Optional;

@SpringBootApplication
public class AttributeConverterApplication implements CommandLineRunner {

    @Autowired
    private ARepository aRepository;

    public static void main(String[] args) throws Exception {

        org.h2.tools.Server server = org.h2.tools.Server.createTcpServer().start();

        SpringApplication.run(AttributeConverterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        A a = new A();
        a.setName("alice");
        a.setPayload("something");

        aRepository.save(a);

        System.out.println(a + " saved");

        long id = a.getId();


        Optional<A> o = aRepository.findById(id);

        if (o.isPresent()) {

            A a2 = o.get();

            System.out.println("retrieved from DB: " + a2);
        }
        else {

            System.out.println("id " + id + " not found");
        }
    }
}
