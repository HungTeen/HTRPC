package love.pangteen.remoting;

import love.pangteen.provider.local.LocalServiceDiscovery;
import love.pangteen.remoting.dto.RpcRequest;
import love.pangteen.remoting.transport.socket.SocketRpcClient;
import love.pangteen.remoting.transport.socket.SocketRpcServer;
import love.pangteen.utils.Util;
import love.pangteen.utils.factory.SingletonFactory;
import org.junit.Test;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:53
 **/
public class SocketRpcTest {

    @Test
    public void testServer() {
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        socketRpcServer.start();
    }

    @Test
    public void testClient() {
        SocketRpcClient socketRpcClient = new SocketRpcClient();
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("love.pangteen.proxy_demo.SmsService")
                .methodName("send")
                .parameters(new Object[]{"hello"})
                .paramTypes(new Class[]{String.class})
                .build();
        Object result = socketRpcClient.sendRpcRequest(rpcRequest, Util.localAddress(6666));
        System.out.println(result);
    }
}
