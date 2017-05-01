
# EJB Remote Invocations over REST

An example of how an EJB application can be converted to make remote invocations over a
REST layer, instead of its native EJB/Remoting transport. 

# NOKB

https://kb.novaordis.com/index.php/EJB_Remote_Invocations_over_REST

# To Run as EJB

In CallerImpl.java:

Uncomment

````
@EJB(lookup = "ejb:/callee/CalleeImpl!io.novaordis.playground.jee.ejb.ejb2rest.common.Callee")
````

and comment

````
@Inject
````

# To Run as REST

In CallerImpl.java:

Comment

````
@EJB(lookup = "ejb:/callee/CalleeImpl!io.novaordis.playground.jee.ejb.ejb2rest.common.Callee")
````

and uncomment

````
@Inject
````

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

# Workflow

````
mvn clean install
./bin/deploy-callee
./bin/deploy-caller
./bin/invoke
````

The caller should bind on standard ports, and the callee should bind on a port set with a 200 offset.


