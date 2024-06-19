package love.pangteen.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.utils.factory.ThreadPoolFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:51
 **/
@Slf4j
public class SocketRpcServer {

    private final ExecutorService threadPool;

    public SocketRpcServer() {
        this.threadPool = ThreadPoolFactory.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
    }

    public void start(){
        try (ServerSocket serverSocket = new ServerSocket()) {
            InetSocketAddress address = ConfigManager.localRpcServiceAddress();
            serverSocket.bind(address);
            log.info("server started on port {}", address.getPort());
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandler(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur exception when server running: ", e);
        }
    }
}
