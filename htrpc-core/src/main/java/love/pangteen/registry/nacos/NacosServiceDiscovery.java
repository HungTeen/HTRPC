package love.pangteen.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import love.pangteen.registry.ServiceDiscovery;

import java.net.InetSocketAddress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 10:56
 **/
public class NacosServiceDiscovery implements ServiceDiscovery {

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        NamingService naming = NacosManager.getNamingService();
        try {
            Instance instances = naming.selectOneHealthyInstance(rpcServiceName);
            return new InetSocketAddress(instances.getIp(), instances.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}
