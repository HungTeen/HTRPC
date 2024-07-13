package love.pangteen.provider.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.provider.ServiceDiscovery;
import love.pangteen.utils.TimeAnalyzer;

import java.net.InetSocketAddress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 10:56
 **/
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        TimeAnalyzer analyzer = new TimeAnalyzer();
        NamingService naming = NacosManager.getNamingService();
        log.info("get naming service cost time: [{}]", analyzer.query());
        try {
            Instance instances = naming.selectOneHealthyInstance(rpcServiceName);
            log.info("select one service server cost time: [{}]", analyzer.query());
            return new InetSocketAddress(instances.getIp(), instances.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}
