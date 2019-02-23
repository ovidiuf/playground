package playground.swagger;

import io.swagger.models.Info;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import playground.swagger.extensions.XAmazonAPIGatewayIntegration;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Ovidiu Feodorov <ofeodorov@uplift.com>
 * @since 2019-02-22
 */
public class AmazonAPIGatewaySpecificationBuilder {

    private static final Logger log = LoggerFactory.getLogger(AmazonAPIGatewaySpecificationBuilder.class);

    private File swaggerFile;
    private String title;
    private String description;
    private String integrationEndpoint;

    /**
     * The base Swagger (OpenAPI 2.0) specification.
     */
    public AmazonAPIGatewaySpecificationBuilder withSwaggerFile(File swaggerFile) {

        this.swaggerFile = swaggerFile;
        return this;
    }

    /**
     * The base OpenAPI 3.0 specification.
     */
    public AmazonAPIGatewaySpecificationBuilder withOpenAPIFile(File openAPIFile) {

        throw new RuntimeException("NOT YET IMPLEMENTED");
    }

    public AmazonAPIGatewaySpecificationBuilder withTitle(String s) {

        this.title = s;
        return this;
    }

    public AmazonAPIGatewaySpecificationBuilder withDescription(String s) {

        this.description = s;
        return this;
    }

    public AmazonAPIGatewaySpecificationBuilder withIntegrationEndpoint(String s) {

        this.integrationEndpoint = s;
        return this;
    }

    public Swagger build() throws AmazonAPIGatewaySpecificationException {

        if (swaggerFile == null) {

            throw new AmazonAPIGatewaySpecificationException("no Swagger file specified");

        }

        if (!swaggerFile.isFile()) {

            throw new AmazonAPIGatewaySpecificationException(swaggerFile + " is not a file");

        }

        if (!swaggerFile.canRead()) {

            throw new AmazonAPIGatewaySpecificationException("Swagger file " + swaggerFile + " cannot be read");
        }

        final SwaggerParser parser = new SwaggerParser();

        // TODO what kinds of exception does this throw. Must wrap in AmazonAPIGatewaySpecificationException

        Swagger swagger = parser.read(swaggerFile.getPath());

        if (integrationEndpoint == null) {

            throw new AmazonAPIGatewaySpecificationException("no integration endpoint specified");
        }

        Map<String, Path> paths = swagger.getPaths();

        for(String pathName: paths.keySet()) {

            Path p = swagger.getPath(pathName);

            List<Operation> operations = p.getOperations();

            for(Operation o: operations) {

                // TODO summary is probably not the best way to get the operation name

                // TODO replace this with a builder?

                XAmazonAPIGatewayIntegration e = new XAmazonAPIGatewayIntegration(o.getSummary(), integrationEndpoint, pathName);

                o.setVendorExtension(e.getName(), e.getContent());

                log.info("installed \"" + e.getName() + "\" extension for path " + pathName);
            }
        }

        Info info = swagger.getInfo();

        if (title != null) {

            info.setTitle(title);
        }

        if (description != null) {

            info.setDescription(description);
        }

        return swagger;
    }
}
