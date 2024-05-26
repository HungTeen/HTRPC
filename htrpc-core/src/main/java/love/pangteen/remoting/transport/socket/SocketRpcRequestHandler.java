package love.pangteen.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.dto.RpcResponse;
import love.pangteen.remoting.transport.RpcRequestHandler;
import love.pangteen.utils.factory.SingletonFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:00
 **/
@Slf4j
public class SocketRpcRequestHandler implements Runnable {

    private final Socket socket;
    private final RpcRequestHandler requestHandler;

    public SocketRpcRequestHandler(Socket socket) {
        this.socket = socket;
        this.requestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            Object object = objectInputStream.readObject();
            if(object instanceof RpcRequest rpcRequest){
                Object result = requestHandler.handle(rpcRequest);
                objectOutputStream.writeObject(RpcResponse.success(result, rpcRequest.getRequestId()));
                objectOutputStream.flush();
            } else {
                log.error("The type of request is not correct.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
