package playground.swagger;

/**
 * @author Ovidiu Feodorov <ofeodorov@uplift.com>
 * @since 2019-02-22
 */
public enum HttpMethod {

    GET;


    /**
     * @return null if no HTTP method can be inferred from string.
     */
    public static HttpMethod fromString(String s) {


        for(HttpMethod m: values()) {

            if (m.name().equalsIgnoreCase(s)) {

                return m;
            }
        }

        return null;
    }
}
