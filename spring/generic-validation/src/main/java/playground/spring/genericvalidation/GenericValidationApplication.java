package playground.spring.genericvalidation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.spring.genericvalidation.model.CreditCard;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class GenericValidationApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GenericValidationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("...");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreditCard cc = new CreditCard();

        Set<ConstraintViolation<CreditCard>> violations = validator.validate(cc);

        for(ConstraintViolation<CreditCard> v: violations) {

            System.out.println(v.getMessage());
        }

    }
}
