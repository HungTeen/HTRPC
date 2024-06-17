package love.pangteen;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.HTRpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@HTRpcScan
@SpringBootApplication
public class HTRpcClientApplication {

    public static void main(String[] args) {
        // Register service via annotation.
        SpringApplication.run(HTRpcClientApplication.class, args);
        log.info("Client Started !");
    }

}