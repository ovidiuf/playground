package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.protocol.BasicHttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Default configurator, which is intended to be used in the context of a service. It is not
 * expected to be used in maestro or the applet, as these applications don't have the required
 * contextual information available.
 */
@Default
@Dependent
public class ServiceContextConfigurator implements Configurator {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ServiceContextConfigurator.class);

    /**
     * Name of the single-sign-on (SSO) cookie. This cookie name is specific to JBOSS EAP 6, and may
     * not work in EAP 7, or in other EE 6 app servers.
     */
    static final String JSESSIONIDSSO = "JSESSIONIDSSO";

    /**
     * "Basic" as in Basic authentication.
     */
    static final String BASIC = "Basic";

    /**
     * Authorization header name.
     */
    static final String HEADER_NAME = "Authorization";

    /**
     * The UTF-8 Charset.
     */
    static final Charset UTF8 = Charset.forName("UTF-8");

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Extracts the Basic authorization credentials from the provided request, if present.
     */
    private static Optional<UsernamePasswordCredentials> getCredentials(final HttpServletRequest currentRequest) {

        final String header = currentRequest.getHeader(HEADER_NAME);

        if (header == null) {

            log.trace("Authorization header is missing from current request");
            return Optional.empty();
        }

        // Authorization: Basic base64credentials
        final int expectedLength = 2;
        final String[] typeAndValue = header.split(" ", expectedLength);

        if (typeAndValue.length < expectedLength) {

            // We are intentionally not including the header value in the error message here, so we
            // don't log someone's hashed credentials.
            log.trace("unexpected format in Authorization header");
            return Optional.empty();
        }

        final String type = typeAndValue[0];
        if (!type.equals(BASIC)) {

            // We are intentionally not including the header value in the error message here, so we
            // don't log someone's hashed credentials.
            log.trace("unexpected Authorization type in current request: " + type);
            return Optional.empty();
        }

        final String base64Credentials = typeAndValue[1];
        final String usernamePassword = new String(Base64.getDecoder().decode(base64Credentials), UTF8);

        return Optional.of(new UsernamePasswordCredentials(usernamePassword));
    }


    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Configurator implementation -------------------------------------------------------------------------------------

    @Override
    public BasicHttpContext createContext(HttpHost targetHost) {

        return createTargetConfigurator().createContext(targetHost);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * Selects the target configurator's implementation based on the currently available contextual
     * information (like active HTTP request and system properties).
     */
    Configurator createTargetConfigurator() {

        final Optional<HttpServletRequest> currentRequest =
                HackToInjectHttpServletRequest.getCurrentRequest();

        if (currentRequest.isPresent())
        {
            log.trace("currentRequest is present");

            final Optional<Cookie> ssoCookie =
                    currentRequest.get().getCookies() == null ? Optional.empty()
                            : Stream.of(currentRequest.get().getCookies())
                            .filter((cookie) -> cookie.getName().equals(JSESSIONIDSSO))
                            .findFirst();

            if (ssoCookie.isPresent())
            {
                log.trace("authenticating with {} cookie from currentRequest", JSESSIONIDSSO);
                return new UseCookieConfigurator(ssoCookie.get());
            }

            final Optional<UsernamePasswordCredentials> credentials =
                    getCredentials(currentRequest.get());

            if (credentials.isPresent())
            {
                log.trace("authenticating using basic auth credentials from currentRequest");
                return new BasicAuthConfigurator(
                        credentials.get().getUserName(),
                        credentials.get().getPassword());
            }

            log.trace(
                    "not authenticating, because no authentication information was found in currentRequest");
            return new NoAuthenticationConfigurator();
        }
        else
        {
            log.trace("currentRequest is absent");
            log.trace("authenticating as system user");
            return new SystemUserConfigurator();
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
