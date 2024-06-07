package love.pangteen.provider.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import love.pangteen.provider.ServiceRegistry;

import java.net.InetSocketAddress;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/29 15:30
 **/
public class NacosServiceRegistry implements ServiceRegistry {

    @Override
    public void registerService(String rpcServiceName, InetSocketAddress address) {
        NamingService naming = NacosManager.getNamingService();

        try {
            naming.registerInstance(rpcServiceName, address.getAddress().getHostAddress(), address.getPort());
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }

//        Instance instance = new Instance();
//        instance.setIp("55.55.55.55");
//        instance.setPort(9999);
//        instance.setHealthy(false);
//        instance.setWeight(2.0);
//        Map<String, String> instanceMeta = new HashMap<>();
//        instanceMeta.put("site", "et2");
//        instance.setMetadata(instanceMeta);
//
//        Service service = new Service("nacos.test.4");
//        service.setApp("nacos-naming");
//        service.sethealthCheckMode("server");
//        service.setEnableHealthCheck(true);
//        service.setProtectThreshold(0.8F);
//        service.setGroup("CNCF");
//        Map<String, String> serviceMeta = new HashMap<>();
//        serviceMeta.put("symmetricCall", "true");
//        service.setMetadata(serviceMeta);
//        instance.setService(service);
//
//        Cluster cluster = new Cluster();
//        cluster.setName("TEST5");
//        AbstractHealthChecker.Http healthChecker = new AbstractHealthChecker.Http();
//        healthChecker.setExpectedResponseCode(400);
//        healthChecker.setCurlHost("USer-Agent|Nacos");
//        healthChecker.setCurlPath("/xxx.html");
//        cluster.setHealthChecker(healthChecker);
//        Map<String, String> clusterMeta = new HashMap<>();
//        clusterMeta.put("xxx", "yyyy");
//        cluster.setMetadata(clusterMeta);
//
//        instance.setCluster(cluster);
//
//        naming.registerInstance("nacos.test.4", instance);
    }
}
