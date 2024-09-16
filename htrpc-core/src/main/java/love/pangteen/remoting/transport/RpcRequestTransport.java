package love.pangteen.remoting.transport;

import love.pangteen.annotations.SPI;
import love.pangteen.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:27
 **/
@SPI
public interface RpcRequestTransport {

    /**
     * Send rpc request to server and get result.
     * @param rpcRequest message body.
     * @param serviceAddress target ip and port.
     * @return result of rpc.
     */
    Object sendRpcRequest(RpcRequest rpcRequest, InetSocketAddress serviceAddress);
}
