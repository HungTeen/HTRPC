package love.pangteen.provider.nacos;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.config.RpcServiceConfig;
import love.pangteen.enums.RpcErrorMessage;
import love.pangteen.exception.RpcException;
import love.pangteen.provider.ServiceProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/3 15:52
 **/
@Slf4j
public class NacosServiceProvider implements ServiceProvider {

    private final Map<String, Object> serviceMap;

    public NacosServiceProvider() {
        this.serviceMap = new ConcurrentHashMap<>();
    }

    @Override
    public Object getService(String rpcServiceName) {
        if (serviceMap.containsKey(rpcServiceName)) {
            return serviceMap.get(rpcServiceName);
        }
        throw new RpcException(RpcErrorMessage.SERVICE_CAN_NOT_BE_FOUND);
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        serviceMap.put(rpcServiceConfig.getRpcServiceName(), rpcServiceConfig.getService());
        ConfigManager.getServiceRegistry().registerService(rpcServiceConfig.getRpcServiceName(), ConfigManager.localRpcServiceAddress());
        log.info("Service: [{}] is published with interfaces [{}]", rpcServiceConfig.getRpcServiceName(), rpcServiceConfig.getService().getClass().getInterfaces());
    }

}
