package love.pangteen;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.config.HTRpcConfig;
import love.pangteen.remoting.transport.netty.server.NettyRpcServer;
import love.pangteen.remoting.transport.socket.SocketRpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

import java.util.concurrent.CompletableFuture;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/17 15:32
 **/
@Slf4j
@AutoConfigureAfter(HTRpcBeanRegister.class)
public class HTRpcBeanInject {

    static {
        log.info("HTRpcBeanInject is initialized !");
    }

    @Autowired
    public void injectConfig(HTRpcConfig htRpcConfig){
        if(htRpcConfig != null) {
            ConfigManager.setConfig(htRpcConfig);
        }
        CompletableFuture.runAsync(() -> {
            switch (ConfigManager.getRequestTransportType()) {
                case SOCKET -> startSocketRpcServer();
                case NETTY -> startNettyRpcServer();
            }
        });
    }

    public static void startSocketRpcServer() {
        SocketRpcServer server = new SocketRpcServer();
        server.start();
        log.info("Socket Server Starting !");
    }

    public static void startNettyRpcServer() {
        NettyRpcServer server = new NettyRpcServer();
        server.start();
        log.info("Netty Server Starting !");
    }

}
