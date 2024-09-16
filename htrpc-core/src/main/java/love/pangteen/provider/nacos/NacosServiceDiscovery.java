package love.pangteen.provider.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.enums.LoadBalanceType;
import love.pangteen.provider.ServiceDiscovery;
import love.pangteen.utils.TimeAnalyzer;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 10:56
 **/
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        NamingService naming = NacosManager.getNamingService();
        try {
            Instance instance = null;
            if(ConfigManager.getLoadBalanceType() == LoadBalanceType.DEFAULT){
                instance = naming.selectOneHealthyInstance(rpcServiceName);
            } else {
                instance = selectInstance(naming.getAllInstances(rpcServiceName), ConfigManager.getLoadBalanceType());
            }
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    private Instance selectInstance(List<Instance> instances, LoadBalanceType loadBalanceType){
        if(instances.isEmpty()) {
            throw new RuntimeException("No available service instances");
        }
        switch (loadBalanceType){
            case RANDOM:
                return instances.get((int) (Math.random() * instances.size()));
            default:
                return instances.getFirst();
        }
    }
}
