package playground.spring.propertyinjection.profiles;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Component
@ConfigurationProperties(prefix = "playground")
public class SimplePropertyHolder {

    public static final String DEFAULT_COLOR = "blue";

    private String color = DEFAULT_COLOR;
}
