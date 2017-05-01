
# EJB Remote Invocations over REST

See the README from the parent directory: https://github.com/NovaOrdis/playground/tree/master/jee/ejb/ejb-over-rest/README.md

# Required Changes

* The business interfaces of the target services must be annotated with JAX-RS annotations (business method(s) and
  association to a @Path). Note that the EJB @Remote annotation may remain on the business interface.
  
````
@Remote
@Path("/")
public interface Callee {

    @POST
    @Consumes("application/json")
    String businessMethodA(String arg);
}
````

* The target service must be deployed in such a way that it bootstraps a JAX-RS service endpoint (WAR instead of
  EJB JAR)




