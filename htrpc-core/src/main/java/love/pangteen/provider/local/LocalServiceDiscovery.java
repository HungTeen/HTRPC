package love.pangteen.provider.local;

import love.pangteen.constant.RpcProperties;
import love.pangteen.provider.ServiceDiscovery;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:59
 **/
public class LocalServiceDiscovery implements ServiceDiscovery {

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        try {
            return new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), RpcProperties.PORT);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}