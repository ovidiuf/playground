package playground.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "playground.spring")
public class SimpleConfigurationPropertyHolder {

    private String color;

    public SimpleConfigurationPropertyHolder() {

        //
        // This is the default value and it should be overwritten by the value maintained in
        // src/test/resources/application.yml
        //

        this.color = "blue";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
