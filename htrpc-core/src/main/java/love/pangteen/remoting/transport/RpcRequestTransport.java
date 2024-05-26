package love.pangteen.remoting.transport;

import love.pangteen.annotations.SPI;
import love.pangteen.remoting.dto.RpcRequest;

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
     * @return result of rpc.
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
