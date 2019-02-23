package playground.swagger;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {

            throw new Exception("missing file path");
        }

        String filePath = args[0];

        final SwaggerParser parser = new SwaggerParser();

        Swagger swagger = parser.read(filePath);

//        String s = YamlExamples.convertToYamlString(swagger);
//        System.out.println(s);

        String s = JsonExamples.convertToJsonString(swagger);
        System.out.println(s);
    }
}
