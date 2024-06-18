package love.pangteen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@EnableHTRPC
@SpringBootApplication
public class HTRpcServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HTRpcServerApplication.class, args);
        log.info("Server Started !");
    }

}