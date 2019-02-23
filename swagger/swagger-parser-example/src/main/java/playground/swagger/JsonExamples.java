package playground.swagger;

import io.swagger.models.Swagger;
import io.swagger.util.Json;
import io.swagger.util.Yaml;

/**
 * @since 2019-02-22
 */
public class JsonExamples {

    public static String convertToJsonString(Swagger swagger) throws Exception {

        return Json.mapper().writeValueAsString(swagger);
    }
}
