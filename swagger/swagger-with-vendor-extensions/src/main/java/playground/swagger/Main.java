package playground.swagger;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;

import java.io.File;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {

            throw new Exception("missing file path");
        }

        String filePath = args[0];

        final SwaggerParser parser = new SwaggerParser();

        Swagger original = parser.read(filePath);

        //
        // write the original
        //

        Files.write(new File("./processed-original.json").toPath(), Json.pretty(original).getBytes());

        AmazonAPIGatewayExtensionManager em = new AmazonAPIGatewayExtensionManager();

        Swagger withVendorExtensions = em.applyExtensions(original);

        Files.write(new File("./with-vendor-extensions.json").toPath(), Json.pretty(withVendorExtensions).getBytes());
    }
}
