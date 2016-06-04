#Simplest RestEasy Example

##Overview

This is one of the simplest possible RestEasy example. It consists of a JAX-RS application that contains just a
single resource. We don't rely on the RestEasy runtime to scan the deployment artifact for resources, we provide it
explicitly via the Application's

    public Set<Class<?>> getClasses()

We also we don't rely on the RestEasy runtime to infer what the application is, we specify it explicitly in web.xml:

    <servlet>
        <servlet-name>Simplest JAXRS Application</servlet-name>
        <servlet-class>
            org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher
        </servlet-class>
        <init-param>
            <param-name>javax.ws.rs.Application</param-name>
            <param-value>io.novaordis.playground.jboss.resteasy.simplest.SimplestApplication</param-value>
        </init-param>
    </servlet>
    
Aside from the deployment descriptor's &lt;servlet-class&gt;, we don't use any RestEasy-specific details, the code is fully
JAX-RS compliant.    

##To Build

    mvn clean package

##To Deploy

    cp ./target/simplest-rest.war $JBOSS_HOME/standalone/deployments

##To Execute

http://localhost:8080/simplest-rest/simplest-resource?key=A









