package love.pangteen.loadbalance;

import love.pangteen.annotations.SPI;
import love.pangteen.remoting.dto.RpcRequest;

import java.util.List;

/**
 * Interface for load balancing strategy.
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 11:02
 **/
@SPI
public interface LoadBalance {

    /**
     * Choose one from the list of existing service addresses list
     *
     * @param serviceUrlList Service address list
     * @param rpcRequest     Request object
     * @return target service address
     */
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}
