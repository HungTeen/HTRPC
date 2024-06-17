package love.pangteen.provider.local;

import love.pangteen.config.ConfigManager;
import love.pangteen.provider.ServiceDiscovery;

import java.net.InetSocketAddress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:59
 **/
public class LocalServiceDiscovery implements ServiceDiscovery {

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        return ConfigManager.localRpcServiceAddress();
    }
}
