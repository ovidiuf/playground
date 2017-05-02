package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import io.novaordis.playground.jee.ejb.ejb2rest.jackson.BasicObjectMapperProvider;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Default implementation of {@link RestClientFactory}.
 */
@Default
@ApplicationScoped
public class RestClientFactoryImpl implements RestClientFactory {

    // Constants -------------------------------------------------------------------------------------------------------

    // OPTIMIZED
    /**
     * The HttpContext that will be used for all requests.
     */
    private static final ThreadLocalHttpContext CONTEXT = new ThreadLocalHttpContext();

    /**
     * The client executor that will be used for all requests.
     */
    private static final ClientExecutor EXECUTOR = new LoggingClientExecutor(
            new ApacheHttpClient4Executor(HttpClients.createSystem(), CONTEXT));

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    /**
     * The {@link Configurator} to be used for creating clients.
     */
    @Inject
    private Configurator configurator;

    // OPTIMIZED
    /**
     * The {@link ResteasyProviderFactory} to be used for each client proxy we create.
     */
    private ResteasyProviderFactory providerFactory;

    // Constructors ----------------------------------------------------------------------------------------------------

    // RestClientFactory implementation --------------------------------------------------------------------------------

    public <T> T get(Class<T> serviceInterface) {

        return createProxy(serviceInterface);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // OPTIMIZED
    /**
     * Initializes the providerFactory, so that it will be ready for use in Resteasy client proxies later on.
     */
    @PostConstruct
    public void initializeProviderFactory() {

        /*
         * This is a fairly annoying process where we have to --manually-- create the provider
         * factory. We'd just like to configure the object mapper that the Resteasy provider uses,
         * but ... Resteasy will discover the Jackson provider automatically via /META-INF/services

         * and load it automatically --before-- we can configure it. Once added, we can't gain
         * access to it and Resteasy will reject our customized version. So, we're going to create
         * the provider factory from scratch and do the same things that it would do by default, but
         * we won't register the built in providers until we've registered our special ones first.
         */
        providerFactory = new ResteasyProviderFactory();

        final JacksonJsonProvider jacksonProvider = new JacksonJsonProvider();
        jacksonProvider.setMapper(BasicObjectMapperProvider.getInstance().getContext(Void.class));

        providerFactory.registerProviderInstance(jacksonProvider);

        /*
         * OK, now that we have customized Jackson we let the built in (automatically discovered)
         * providers be added to our providerFactory.
         */
        RegisterBuiltin.register(providerFactory);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    /**
     * Creates a proxy that creates a client on every invocation.
     *
     * We "wrap" the actual client in a proxy, so that developers using RestClientFactory do not
     * have to worry about the lifecycle of their clients.
     *
     * If we were to remove the proxy and simply use the clients directly in services we would run
     * the risk of stateless EJBs re-using the same client for multiple EJB/Servlet requests, which
     * would result in downstream calls authenticating as the (wrong) user from a previous request
     * to that EJB.
     */
    private <T> T createProxy(Class<T> serviceInterface) {

        //noinspection unchecked,Convert2Lambda
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(final Object proxy, final Method method, final Object[] args)
                            throws Throwable {

                        final T restEasyClient = createInternalRestEasyClient(serviceInterface);

                        try {

                            return method.invoke(restEasyClient, args);

                        } catch (final InvocationTargetException e) {

                            throw e.getCause();
                        }
                    }
                });
    }

    /**
     * Creates a RestEasy client for a given service.
     */
    private <T> T createInternalRestEasyClient(final Class<T> serviceInterface) throws URISyntaxException {

        final URL location = new ServiceLocator().lookupService(serviceInterface);

        CONTEXT.setTarget(
                configurator.createContext(
                        new HttpHost(
                                location.getHost(),
                                location.getPort(),
                                location.getProtocol())));

        return ProxyFactory.create(serviceInterface, location.toURI(), EXECUTOR, providerFactory);

    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
