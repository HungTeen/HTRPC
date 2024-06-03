package love.pangteen.provider;

import love.pangteen.config.RpcServiceConfig;

/**
 * Store and provide services.
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 9:52
 **/
public interface ServiceProvider {

    /**
     * @param rpcServiceConfig rpc service related attributes
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName rpc service name
     * @return service object
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig rpc service related attributes
     */
    void publishService(RpcServiceConfig rpcServiceConfig);
}
