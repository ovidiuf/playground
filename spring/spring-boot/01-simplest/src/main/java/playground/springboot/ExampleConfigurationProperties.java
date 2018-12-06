package playground.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("playground")
public class ExampleConfigurationProperties {

    private String color;

    public void setColor(String color) {

        this.color = color;
    }

    public String getColor() {

        return color;
    }
}
