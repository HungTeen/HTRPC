package love.pangteen.remoting.transport;

import lombok.extern.slf4j.Slf4j;
import love.pangteen.config.ConfigManager;
import love.pangteen.provider.ServiceProvider;
import love.pangteen.remoting.dto.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/5/26 10:08
 **/
@Slf4j
public class RpcRequestHandler {

    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        this.serviceProvider = ConfigManager.getServiceProvider();
    }

    /**
     * Processing rpcRequest: call the corresponding method, and then return the method.
     */
    public Object handle(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * Call the corresponding method by reflection.
     * @param rpcRequest rpc request object.
     * @param service service object.
     * @return result of the method.
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service: [{}] successful invoke method: [{}]", rpcRequest.getRpcServiceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
