package love.pangteen.remoting.transport.socket;

import love.pangteen.exception.RpcException;
import love.pangteen.registry.ServiceDiscovery;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.transport.RpcRequestTransport;

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
public class SocketRpcClient implements RpcRequestTransport {

    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery; // TODO Extension Loader.
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        InetSocketAddress serviceAddress = serviceDiscovery.lookupService(rpcRequest.getRpcServiceName());
        try(Socket socket = new Socket()) {
            socket.connect(serviceAddress);

            // Send rpc request to server.
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);

            // Get result from server.
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败", e);
        }
    }
}
