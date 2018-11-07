package playground;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Red red() {

        return new Red("washed out");
    }

    @Bean
    public Blue blue(Red red) {

        return new Blue(red, "strong");
    }
}
