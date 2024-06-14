package love.pangteen.provider.nacos;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.RpcServiceConfig;
import love.pangteen.constant.RpcProperties;
import love.pangteen.enums.RpcErrorMessage;
import love.pangteen.exception.RpcException;
import love.pangteen.provider.ServiceProvider;
import love.pangteen.provider.ServiceRegistry;
import love.pangteen.utils.Util;
import love.pangteen.utils.extension.ExtensionLoader;

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
    private final ServiceRegistry serviceRegistry;

    public NacosServiceProvider() {
        this.serviceMap = new ConcurrentHashMap<>();
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(RpcProperties.SERVICE_PROVIDER_TYPE.getName());
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
        serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), Util.localAddress(RpcProperties.PORT));
        log.info("Service: [{}] is published with interfaces [{}]", rpcServiceConfig.getRpcServiceName(), rpcServiceConfig.getService().getClass().getInterfaces());
    }

}
