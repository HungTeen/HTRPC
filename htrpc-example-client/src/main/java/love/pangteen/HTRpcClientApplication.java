package love.pangteen;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.HTRpcScan;
import love.pangteen.controller.WorkController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@HTRpcScan
public class HTRpcClientApplication {

    public static void main(String[] args) {
        // Register service via annotation.
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(HTRpcClientApplication.class);
        log.info("Client Starting !");
        WorkController workController = applicationContext.getBean(WorkController.class);
        workController.doWorkDayAndNight();
    }

}