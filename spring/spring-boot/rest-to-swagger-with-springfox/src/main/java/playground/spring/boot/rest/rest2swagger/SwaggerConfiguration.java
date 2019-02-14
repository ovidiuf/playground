package playground.spring.boot.rest.rest2swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @author Ovidiu Feodorov <ofeodorov@feodorov.com>
 * @since 2019-02-14
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfiguration(TypeResolver typeResolver) {

        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket aApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        // ApiSelectorBuilder gives fine grained control over the endpoints exposed via swagger
        ApiSelectorBuilder apiSelectorBuilder = docket.select();
        apiSelectorBuilder.
                apis(RequestHandlerSelectors.any()).
                paths(PathSelectors.any()).
                build();

        // alternatives:
        //  apis(RequestHandlerSelectors.basePackage("playground.example.controllers"))
        //  paths(regex("/product.*"))

        // adds a servlet path mapping, when the servlet has a path mapping. This prefixes paths with the provided
        // path mapping
        docket.pathMapping("/").
                directModelSubstitute(LocalDate.class, String.class).
                genericModelSubstitutes(ResponseEntity.class).
                alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class))).
                // indicates whether default HTTP response code needs to be used or not
                        useDefaultResponseMessages(false).
                // overrides response messages globally for HTTP methods
                        globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder().
                                code(500).
                                message("500 message").
                                responseModel(new ModelRef("Error")).
                                build())).
                // sets up the security schema used to protect the API
                        securitySchemes(newArrayList(apiKey())).
                securityContexts(newArrayList(securityContext())).
                enableUrlTemplating(true).
//                globalOperationParameters(
//                        newArrayList(new ParameterBuilder().
//                                name("someGlobalParameter").
//                                description("description of someGlobalParameter").
//                                modelRef(new ModelRef("string")).parameterType("query").required(true).build())).
        tags(new Tag("A Service", "All APIs relating to A"));

        return docket;
    }

    private ApiKey apiKey() {

        return new ApiKey("mykey", "api_key", "header");
    }

    private SecurityContext securityContext() {

        return SecurityContext.builder().
                securityReferences(defaultAuth()).
                forPaths(PathSelectors.regex("/anyPath.*")).
                build();
    }

    private List<SecurityReference> defaultAuth() {

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return newArrayList(new SecurityReference("mykey", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {

        return SecurityConfigurationBuilder.builder().
                clientId("test-app-client-id").
                clientSecret("test-app-client-secret").
                realm("test-app-realm").
                appName("test-app").scopeSeparator(",").
                additionalQueryStringParams(null).
                useBasicAuthenticationWithAccessCodeGrant(false).
                build();
    }

    @Bean
    UiConfiguration uiConfig() {

        return UiConfigurationBuilder.builder().
                deepLinking(true).
                displayOperationId(false).
                defaultModelsExpandDepth(1).
                defaultModelExpandDepth(1).
                defaultModelRendering(ModelRendering.EXAMPLE).
                displayRequestDuration(false).
                docExpansion(DocExpansion.NONE).
                filter(false).
                maxDisplayedTags(null).
                operationsSorter(OperationsSorter.ALPHA).
                showExtensions(false).
                tagsSorter(TagsSorter.ALPHA).
                supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).
                validatorUrl(null).
                build();
    }

    //
    // this is needed for UI support
    //

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
