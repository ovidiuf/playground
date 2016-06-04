package io.novaordis.playground.jboss.resteasy.simplest.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * This is a root resource class.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 9/25/15
 */
@Path("/simplest-resource")
public class SimplestResource {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(SimplestResource.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @HeaderParam("User-Agent")
    private String userAgent;

    // Constructors ----------------------------------------------------------------------------------------------------

    public SimplestResource() {
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * This is a regular resource method: annotated with a request method designator.
     *
     * The resource is given a key and returns the corresponding value.
     */
    @GET
    public Response get(@QueryParam("key") String key, String entity) {

        if (key == null) {
            Response response = Response.status(400).entity("must provide a key: /simplest?key=...").build();
            throw new WebApplicationException(response);
        }

        return Response.ok().entity("value " + UUID.randomUUID().toString()).build();
    }

    @Override
    public String toString() {
        return "SimplestResource[" + Integer.toHexString(System.identityHashCode(this)) + ", userAgent=" + userAgent + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
