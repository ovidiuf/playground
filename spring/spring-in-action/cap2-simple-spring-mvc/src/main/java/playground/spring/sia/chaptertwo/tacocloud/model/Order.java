package playground.spring.sia.chaptertwo.tacocloud.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Order {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "ZIP is required")
    private String zip;

    @CreditCardNumber(message = "Not a valid CC number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\\\/])([1-9][0-9])$", message="Expiration date should be in format MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;
}
