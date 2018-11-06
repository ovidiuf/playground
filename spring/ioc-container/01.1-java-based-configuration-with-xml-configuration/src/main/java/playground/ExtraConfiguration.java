package playground;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtraConfiguration {

    @Bean
    public Red red() {

        return new Red("bright");
    }
}
