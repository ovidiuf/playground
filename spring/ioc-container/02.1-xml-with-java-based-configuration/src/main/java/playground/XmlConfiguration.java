package playground;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:extra-beans.xml"})
public class XmlConfiguration {
}
