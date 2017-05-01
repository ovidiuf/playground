
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

# Changes from EJB to REST

https://kb.novaordis.com/index.php/EJB_Remote_Invocations_over_REST#Changes_from_EJB_to_REST

# Workflow

````
mvn clean install
./bin/deploy-callee
./bin/deploy-caller
./bin/invoke
````

The caller should bind on standard ports, and the callee should bind on a port set with a 200 offset.


