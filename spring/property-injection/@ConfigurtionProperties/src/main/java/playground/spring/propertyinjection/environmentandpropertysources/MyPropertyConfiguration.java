package playground.spring.propertyinjection.environmentandpropertysources;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@ConfigurationProperties(prefix = "playground.spring.pi")
@Data
@Validated
public class MyPropertyConfiguration {

    public static final int DEFAULT_SIZE = 20;
    public static final String DEFAULT_COLOR = "blue";

    @Min(value = 5, message = "the size must be larger or equal than 5")
    @Max(value = 40, message = "the size must be smaller or equal than 40")
    private int size = DEFAULT_SIZE;
    private String color = DEFAULT_COLOR;
}
