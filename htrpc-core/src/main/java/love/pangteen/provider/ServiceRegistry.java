package love.pangteen.provider;

import love.pangteen.annotations.SPI;

import java.net.InetSocketAddress;

/**
 * 服务注册接口。
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/29 15:28
 **/
@SPI
public interface ServiceRegistry {

    /**
     * Register service to registry center.
     * @param rpcServiceName service name.
     * @param address service address.
     */
    void registerService(String rpcServiceName, InetSocketAddress address);
}
