package playground.swagger;

import io.swagger.models.Swagger;
import io.swagger.util.Json;

import java.io.File;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {

            throw new Exception("missing file path");
        }

        String filePath = args[0];

        AmazonAPIGatewaySpecificationBuilder amazonSpecBuilder = new AmazonAPIGatewaySpecificationBuilder();

        Swagger amazonSwagger =
                amazonSpecBuilder.
                        withSwaggerFile(new File(filePath)).
                        withIntegrationEndpoint("http://playground-nlb-95d74901c7b728b1.elb.us-west-2.amazonaws.com:10001").
                        withTitle("themyscira").
                        withDescription("Themyscira API").
                        build();

        Files.write(new File("./with-vendor-extensions.json").toPath(), Json.pretty(amazonSwagger).getBytes());
    }
}
