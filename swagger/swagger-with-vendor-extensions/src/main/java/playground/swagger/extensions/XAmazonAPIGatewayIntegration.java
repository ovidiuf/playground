package playground.swagger.extensions;

import playground.swagger.AmazonAPIGatewaySpecificationException;
import playground.swagger.HttpMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ovidiu Feodorov <ofeodorov@uplift.com>
 * @since 2019-02-22
 */
public class XAmazonAPIGatewayIntegration {

    private HttpMethod method;
    private String integrationEndpoint;
    private String path;

    public XAmazonAPIGatewayIntegration(String method, String integrationEndpoint, String path)
            throws AmazonAPIGatewaySpecificationException {

        this.method = HttpMethod.fromString(method);

        if (this.method == null) {

            throw new AmazonAPIGatewaySpecificationException("no such HTTP method: " + method);
        }
        this.integrationEndpoint = integrationEndpoint;
        this.path = path;
    }

    public String getName() {

        return "x-amazon-apigateway-integration";
    }

    public Map<String, String> getContent() {

        Map<String, String> m = new HashMap<>();

        m.put("uri", integrationEndpoint + (path.startsWith("/") ? "" : "/") + path);
        m.put("httpMethod",method.name());
        m.put("passthroughBehavior", "when_no_match");
        m.put("type", "http_proxy");

        return m;
    }
}
