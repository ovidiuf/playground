package playground.spring.boot.rest.rest2swagger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Ovidiu Feodorov <ofeodorov@uplift.com>
 * @since 2019-02-14
 */
@SpringBootTest(classes = RestToSwaggerApplication.class)
@RunWith(SpringRunner.class)
public class SwaggerSpecificationGenerator {

    private static final String API_URI = "/v2/api-docs";

    private String swaggerOutputPath = "./build";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void generateSwaggerSpecification() throws Exception {

        ResultHandler rh = r -> {

            String swaggerJSONAsString = r.getResponse().getContentAsString();

            File swaggerDir = new File(swaggerOutputPath);

            //noinspection ResultOfMethodCallIgnored
            swaggerDir.mkdirs();

            File swaggerFile = new File(swaggerDir, "swagger.json");

            assertTrue(swaggerFile.createNewFile());

            Files.write(Paths.get(swaggerFile.getAbsolutePath()), swaggerJSONAsString.getBytes(StandardCharsets.UTF_8));
        };

        mockMvc.perform(get(API_URI).accept(MediaType.APPLICATION_JSON)).andDo(rh);
    }
}
