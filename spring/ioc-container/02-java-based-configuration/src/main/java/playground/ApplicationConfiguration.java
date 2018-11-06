package playground;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
public class ApplicationConfiguration {

    /**
     * <bean name="red" class="playground.Red">
     *   <constructor-arg value = "strong" />
     * </bean>
     */
    @Bean
    public Red red() {

        return new Red("bright");
    }

    /**
     * <bean name="blue" class="playground.Blue">
     *   <constructor-arg ref = "red" />
     *   <constructor-arg value = "pale" />
     * </bean>
     */
    @Bean
    public Blue blue(Red red) {

        return new Blue(red, "strong");
    }

    /**
     * <bean name="green" class="playground.Green">
     *   <constructor-arg value = "transparent" />
     *   <property name = "red" ref = "red" />
     * </bean>
     */
    @Bean
    public Green green(Red red) {

        Green green = new Green("washed");
        green.setRed(red);
        return green;
    }

}
