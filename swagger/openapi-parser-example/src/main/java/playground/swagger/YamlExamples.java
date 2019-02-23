package playground.swagger;

import io.swagger.models.Swagger;
import io.swagger.util.Yaml;

/**
 * @since 2019-02-22
 */
@Deprecated
public class YamlExamples {

    public static String convertToYamlString(Swagger swagger) throws Exception {

        return Yaml.mapper().writeValueAsString(swagger);
    }
}
