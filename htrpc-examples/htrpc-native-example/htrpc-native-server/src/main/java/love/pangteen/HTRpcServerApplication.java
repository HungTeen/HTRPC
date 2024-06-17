package love.pangteen;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.HTRpcScan;
import love.pangteen.remoting.transport.netty.server.NettyRpcServer;
import love.pangteen.remoting.transport.socket.SocketRpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@HTRpcScan
public class HTRpcServerApplication {

    public static void main(String[] args) {
        // Register service via annotation.
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(HTRpcServerApplication.class);
        log.info("Server Starting !");
//        startSocketRpcServer();
        startNettyRpcServer();
    }

    public static void startSocketRpcServer() {
        SocketRpcServer server = new SocketRpcServer();
        server.start();
    }

    public static void startNettyRpcServer() {
         NettyRpcServer server = new NettyRpcServer();
         server.start();
    }

}