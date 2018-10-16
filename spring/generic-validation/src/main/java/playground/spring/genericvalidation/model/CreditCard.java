package playground.spring.genericvalidation.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreditCard {

    //
    // the credit card number
    //

    private long number;

    //
    // expiration
    //

    private String expirationMonth;

    private int expirationYear;

    //
    // card verification value
    //

    private int cvv;

    @NotNull(message = "The first name cannot be null")
    private String firstName;

    private String lastName;

    private int zip;

}
