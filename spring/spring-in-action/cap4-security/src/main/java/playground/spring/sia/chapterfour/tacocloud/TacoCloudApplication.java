package playground.spring.sia.chapterfour.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) throws Exception {

        org.h2.tools.Server.createTcpServer().start();

        SpringApplication.run(TacoCloudApplication.class, args);
    }
}
