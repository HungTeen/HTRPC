package love.pangteen.registry;

import love.pangteen.constant.RpcProperties;
import love.pangteen.registry.nacos.NacosServiceDiscovery;
import love.pangteen.registry.nacos.NacosServiceRegistry;
import love.pangteen.utils.Util;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/30 10:39
 **/
public class NacosServiceTest {

    @Test
    public void registerService() {
        final String serviceName = "Test Service";
        InetSocketAddress serviceAddress = Util.localAddress(RpcProperties.PORT);

        NacosServiceRegistry registry = new NacosServiceRegistry();
        registry.registerService(serviceName, serviceAddress);

        try {
            Thread.sleep(2000); // 等待Nacos响应完成。
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        NacosServiceDiscovery discovery = new NacosServiceDiscovery();
        InetSocketAddress lookupAddress = discovery.lookupService(serviceName);

        Assert.assertEquals(serviceAddress, lookupAddress);
    }

}
