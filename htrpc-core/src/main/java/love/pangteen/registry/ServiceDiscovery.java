package love.pangteen.registry;

import love.pangteen.annotations.SPI;

import java.net.InetSocketAddress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:39
 **/
@SPI
public interface ServiceDiscovery {

    /**
     * Lookup service by rpcServiceName.
     * @param rpcServiceName rpc service name.
     * @return service address.
     */
    InetSocketAddress lookupService(String rpcServiceName);
}
