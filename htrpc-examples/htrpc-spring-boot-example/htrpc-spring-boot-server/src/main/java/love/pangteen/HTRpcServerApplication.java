package love.pangteen;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.HTRpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@HTRpcScan
@SpringBootApplication
public class HTRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HTRpcServerApplication.class, args);
        log.info("Server Started !");
    }

}