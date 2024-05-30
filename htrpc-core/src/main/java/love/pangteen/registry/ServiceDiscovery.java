package love.pangteen.registry;

import love.pangteen.annotations.SPI;

import java.net.InetSocketAddress;

/**
 * 服务发现接口。
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:39
 **/
@SPI
public interface ServiceDiscovery {

    /**
     * Lookup service ip address by rpcServiceName.
     * @param rpcServiceName rpc service name.
     * @return service address.
     */
    InetSocketAddress lookupService(String rpcServiceName);
}
