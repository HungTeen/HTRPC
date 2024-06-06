package love.pangteen;

import love.pangteen.annotations.HTRpcScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@HTRpcScan
public class ClientApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientApplication.class);
        System.out.println("Hello world!");
    }

}