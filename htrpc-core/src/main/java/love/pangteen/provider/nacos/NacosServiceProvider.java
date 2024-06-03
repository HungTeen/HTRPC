package love.pangteen.provider.nacos;

import love.pangteen.config.RpcServiceConfig;
import love.pangteen.provider.ServiceProvider;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/3 15:52
 **/
public class NacosServiceProvider implements ServiceProvider {
    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {

    }

    @Override
    public Object getService(String rpcServiceName) {
        return null;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {

    }
}
