package love.pangteen.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.exception.RpcException;
import love.pangteen.provider.ServiceDiscovery;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.transport.RpcRequestTransport;
import love.pangteen.utils.TimeAnalyzer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:36
 **/
@Slf4j
public class SocketRpcClient implements RpcRequestTransport {

    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this(ConfigManager.getServiceDiscovery());
    }

    public SocketRpcClient(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        TimeAnalyzer analyzer = new TimeAnalyzer();
        InetSocketAddress serviceAddress = serviceDiscovery.lookupService(rpcRequest.getRpcServiceName());

        log.info("lookup service server cost time: [{}]", analyzer.query());

        try(Socket socket = new Socket()) {

            socket.connect(serviceAddress);
            log.info("connect to server cost time: [{}]", analyzer.query());
            // Send rpc request to server.

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            log.info("write object cost time: [{}]", analyzer.query());

            // Get result from server.
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object result = objectInputStream.readObject();
            log.info("read object cost time: [{}]", analyzer.query());
            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败", e);
        }
    }
}
