package love.pangteen;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@EnableDubbo
@SpringBootApplication
public class DubboServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DubboServerApplication.class, args);
        log.info("Server Started !");
    }

}