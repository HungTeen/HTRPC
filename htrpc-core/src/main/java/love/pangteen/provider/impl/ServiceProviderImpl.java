package love.pangteen.provider.impl;

import love.pangteen.config.RpcServiceConfig;
import love.pangteen.provider.ServiceProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:50
 **/
public class ServiceProviderImpl implements ServiceProvider {

    private final Map<String, Object> serviceMap;

    public ServiceProviderImpl() {
        this.serviceMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {

    }

    @Override
    public Object getService(String rpcServiceName) {
        Object object = serviceMap.get(rpcServiceName);
//        if(object == null){
//            return new SmsServiceImpl();
////            throw new RpcException(RpcErrorMessage.SERVICE_CAN_NOT_BE_FOUND);
//        }
        return object;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {

    }
}
